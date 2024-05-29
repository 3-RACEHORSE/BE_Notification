package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.dto.AlarmRequestDto;
import com.sparos4th2.alarm.dto.AlarmResponseDto;
import com.sparos4th2.alarm.infrastructure.AlarmRepository;
import com.sparos4th2.alarm.vo.AlarmVo;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService {

	private final AlarmRepository alarmRepository;
	private Sinks.Many<Alarm> sink;

	@PostConstruct
	public void init() {
		sink = Sinks.many().multicast().onBackpressureBuffer();
	}

	@Override
	public void saveAlarm() {
		Alarm alarm = Alarm.builder()
			.receiverUuid("test")
			.message("test")
			.eventType("test")
			.alarmTime(LocalDateTime.now())
			.build();

		log.info("alarm: {}", alarm.toString());
		alarmRepository.save(alarm);

		sink.tryEmitNext(alarm);
	}

	@Override
	public Flux<Alarm> getAlarm(String receiverUuid) {
		return alarmRepository
			.findAlarmByReceiverUuid(receiverUuid)
			.take(10)
			.subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Flux<Alarm> streamAlarms(String receiverUuid) {
		return sink.asFlux().map(data -> data);
	}
}
