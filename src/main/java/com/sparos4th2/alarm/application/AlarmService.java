package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.domain.Alarm;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlarmService {

	void saveAlarm();

	Flux<Alarm> getAlarm(String receiverUuid);

	Flux<ServerSentEvent<Object>> connect(String receiverUuid);

	Mono<Boolean> successMessageSend(String receiverUuid);

	void finish(String receiverUuid);
}