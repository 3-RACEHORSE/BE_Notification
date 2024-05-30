package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.infrastructure.AlarmRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
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
		if (sinks.containsKey(alarm.getReceiverUuid())) {
			sinks.get(alarm.getReceiverUuid())
				.tryEmitNext(ServerSentEvent.builder().event("alarm").data(alarm)
					.comment("new alarm")
					.build());
		}
	}

	@Override
	public Flux<Alarm> getAlarm(String receiverUuid) {
		return alarmRepository
			.findAlarmByReceiverUuid(receiverUuid)
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
		return sink.asFlux().doOnCancel(() -> {
			log.info("### SSE Notification Cancelled by client: " + receiverUuid);
			this.finish(receiverUuid);
		});
	}

	public void finish(String receiverUuid) {
		sinks.get(receiverUuid).tryEmitComplete();
		sinks.remove(receiverUuid);
	}
}
