package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.data.dto.NotificationDto;
import com.sparos4th2.alarm.data.vo.NotificationResponseVo;
import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.domain.AlarmCount;
import com.sparos4th2.alarm.data.dto.AlarmDto;
import com.sparos4th2.alarm.infrastructure.AlarmCountReactiveRepository;
import com.sparos4th2.alarm.infrastructure.AlarmCountRepository;
import com.sparos4th2.alarm.infrastructure.AlarmReactiveRepository;
import com.sparos4th2.alarm.infrastructure.AlarmRepository;
import java.time.LocalDateTime;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {

	private final AlarmCountRepository alarmCountRepository;
	private final AlarmRepository alarmRepository;
	private final Map<String, Sinks.Many<ServerSentEvent<Object>>> sinks = new HashMap<>();

	@Override
	public void saveAlarm() {
		Alarm alarm = Alarm.builder()
			.receiverUuid("test")
			.message("test")
			.eventType("test")
			.alarmUrl("testUrl")
			.alarmTime(LocalDateTime.now())
			.build();
		log.info("alarm >>> {}", alarm.toString());
		alarmRepository.save(alarm);

		AlarmCount alarmCount = AlarmCount.builder()
				.alarmCount(1)
				.receiverUuid("test")
				.build();
		alarmCountRepository.save(alarmCount);
	}

	@Override
	public NotificationResponseVo getAlarm(String receiverUuid, Integer page, Integer size) {
		log.info("receiverUuid >>> {}, page >>> {}, size >>> {}", receiverUuid, page, size);

		// AlarmCount 수를 0으로 갱신
		AlarmCount alarmCount = AlarmCount.builder()
				.receiverUuid(receiverUuid)
				.alarmCount(0)
				.build();

		alarmCountRepository.save(alarmCount);

		// 알람 리스트 최신순으로 반환
		Page<Alarm> alarmPage = alarmRepository.findAllAlarm(
				receiverUuid, PageRequest.of(page, size)
		);

		List<Alarm> alarms = alarmPage.getContent();

		List<NotificationDto> notificationDtos = new ArrayList<>();

		for (Alarm alarm : alarms) {
			notificationDtos.add(NotificationDto.builder()
					.message(alarm.getMessage())
					.eventType(alarm.getEventType())
					.alarmUrl(alarm.getAlarmUrl())
					.alarmTime(alarm.getAlarmTime())
					.build());
		}

		boolean hasNext = alarmPage.hasNext();

		return NotificationResponseVo.builder()
				.notificationDtoList(notificationDtos)
				.currentPage(page)
				.hasNext(hasNext)
				.build();
	}

	public void consume(AlarmDto alarmDto) {
		log.info("Receiver UUIDs -> {}", alarmDto.getReceiverUuids());
		log.info("Consumed message -> {}", alarmDto.getMessage());

		List<String> receiverUuids = alarmDto.getReceiverUuids();

		receiverUuids.forEach(receiverUuid -> {
			Alarm alarm = Alarm.builder()
				.receiverUuid(receiverUuid)
				.message(alarmDto.getMessage())
				.eventType(alarmDto.getEventType())
				.alarmTime(LocalDateTime.now())
				.build();

			// alarm 도큐먼트 저장
			alarmRepository.save(alarm);

			// 기존의 alarmCountRepository에 있는 지 확인한다.
			// 있으면 count + 1, 없으면 count = 1 처리 후 alarm_count 도큐먼트 저장
			Optional<AlarmCount> alarmCount = alarmCountRepository.findByReceiverUuid(receiverUuid);

			if (alarmCount.isPresent()) {
				AlarmCount newAlarmCount = AlarmCount.builder()
					.receiverUuid(receiverUuid)
					.alarmCount(alarmCount.get().getAlarmCount() + 1)
					.build();
				alarmCountRepository.save(newAlarmCount);
			}
			else {
				AlarmCount newAlarmCount = AlarmCount.builder()
					.receiverUuid(receiverUuid)
					.alarmCount(1)
					.build();
				alarmCountRepository.save(newAlarmCount);
			}
		});
	}
}
