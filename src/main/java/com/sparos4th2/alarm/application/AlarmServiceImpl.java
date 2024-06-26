package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.common.exception.CustomException;
import com.sparos4th2.alarm.common.exception.ResponseStatus;
import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.domain.AlarmCount;
import com.sparos4th2.alarm.dto.AlarmDto;
import com.sparos4th2.alarm.infrastructure.AlarmCountRepository;
import com.sparos4th2.alarm.infrastructure.AlarmRepository;
import com.sparos4th2.alarm.vo.AlarmVo;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {

	private final AlarmRepository alarmRepository;
	private final AlarmCountRepository alarmCountRepository;
	private final Map<String, Sinks.Many<ServerSentEvent<Object>>> sinks = new HashMap<>();

	@Override
	public void saveAlarm() {
		Alarm alarm = Alarm.builder()
			.receiverUuid("test")
			.message("test")
			.eventType("test")
			.alarmTime(LocalDateTime.now())
			.build();
		log.info("alarm: {}", alarm.toString());
		alarmRepository.save(alarm).subscribe();

		AlarmVo alarmVo = AlarmVo.builder()
			.receiverUuid("test")
			.message("test")
			.eventType("test")
			.alarmTime(LocalDateTime.now())
			.alarmCount(alarmCountRepository.findByReceiverUuid("test").getAlarmCount())
			.build();

		if (sinks.containsKey(alarm.getReceiverUuid())) {
			sinks.get(alarm.getReceiverUuid())
				.tryEmitNext(ServerSentEvent.builder().event("alarm").data(alarmVo)
					.comment("new alarm")
					.build());
		}
	}

	@Override
	public Flux<Alarm> getAlarm(String receiverUuid) {

		alarmCountRepository.findByReceiverUuid(receiverUuid)
			.switchIfEmpty(Mono.error(new CustomException(ResponseStatus.NO_EXIST_ALARM_COUNT)))
			.subscribe();

		alarmCountRepository.save(AlarmCount.builder()
			.receiverUuid(receiverUuid)
			.alarmCount(0)
			.build()).subscribe();

		return alarmRepository.findAlarmByReceiverUuid(receiverUuid)
			.switchIfEmpty(Mono.error(new CustomException(ResponseStatus.NO_EXIST_ALARM)))
			.take(10)
			.subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Flux<ServerSentEvent<Object>> connect(String receiverUuid) {
		if (sinks.containsKey(receiverUuid)) {  //이미 SSE 연결이 되어있는 경우
			return sinks.get(receiverUuid).asFlux();
		}

		//SSE 연결이 되어있지 않은 경우
		Sinks.Many<ServerSentEvent<Object>> sink = Sinks.many().multicast().onBackpressureBuffer();
		sinks.put(receiverUuid, sink);
		sinks.get(receiverUuid).tryEmitNext(ServerSentEvent.builder()
			.event("config")
			.data("Connected Successfully")
			.comment("Connected Successfully")
			.build());

		//30분 후에 연결이 끊어지도록 설정
		Mono.delay(Duration.ofMinutes(30)).doOnNext(i -> finish(receiverUuid))
			.subscribe();
		return sink.asFlux().doOnCancel(() -> {
			log.info("### SSE Notification Cancelled by client: " + receiverUuid);
			finish(receiverUuid);
		});
	}

//	@Override
//	public Mono<Boolean> successMessageSend(String receiverUuid) {
//		return Mono.just(receiverUuid)
//			.flatMap(id -> {
//				if (sinks.containsKey(receiverUuid)) {      //알림을 받을 사용자가 현재 SSE로 연결한 경우 알림 발송
//					sinks.get(receiverUuid).tryEmitNext(ServerSentEvent.builder()
//						.event("config")
//						.data("Connected Successfully")
//						.comment("Connected Successfully")
//						.build());
//					return Mono.just(true);
//				}
//				//오류처리CustomException으로 해야됨
//				return Mono.error(new RuntimeException("Not connected"));
//			});
//	}

	@Override
	public void finish(String receiverUuid) {
		sinks.get(receiverUuid).tryEmitComplete();
		sinks.remove(receiverUuid);
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

			if (alarmCountRepository.existsByReceiverUuid(receiverUuid).isPresent()) {
				AlarmCount alarmCount = alarmCountRepository.findByReceiverUuid(receiverUuid);
				AlarmCount alarmCount1 = AlarmCount.builder()
					.receiverUuid(receiverUuid)
					.alarmCount(alarmCount.getAlarmCount() + 1)
					.build();
				alarmCountRepository.save(alarmCount1).subscribe();
			}
			else {
				AlarmCount alarmCount = AlarmCount.builder()
					.receiverUuid(receiverUuid)
					.alarmCount(1)
					.build();
				alarmCountRepository.save(alarmCount).subscribe();
			}

			AlarmVo alarmVo = AlarmVo.builder()
				.receiverUuid(receiverUuid)
				.message(alarmDto.getMessage())
				.eventType(alarmDto.getEventType())
				.alarmTime(LocalDateTime.now())
				.alarmCount(alarmCountRepository.findByReceiverUuid(receiverUuid).getAlarmCount())
				.build();

			log.info("alarm: {}", alarm.toString());
			alarmRepository.save(alarm).subscribe();

			if (sinks.containsKey(alarm.getReceiverUuid())) {
				sinks.get(alarm.getReceiverUuid())
					.tryEmitNext(ServerSentEvent.builder().event("alarm").data(alarmVo)
						.comment("new alarm")
						.build());
			}
		});
	}
}
