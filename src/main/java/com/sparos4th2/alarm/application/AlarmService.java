package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.dto.AlarmResponseDto;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface AlarmService {

	void saveAlarm(String receiverUuid);

	Flux<AlarmResponseDto> getAlarm(String receiverUuid);

	Flux<ServerSentEvent<Alarm>> subscribe(String receiverUuid);
}