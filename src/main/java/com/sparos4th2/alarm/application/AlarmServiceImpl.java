package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.dto.AlarmResponseDto;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public class AlarmServiceImpl implements AlarmService {

	@Override
	public void saveAlarm(String receiverUuid) {
		// TODO Auto-generated method stub
	}

	@Override
	public Flux<AlarmResponseDto> getAlarm(String receiverUuid) {
		return null;
	}

	@Override
	public Flux<ServerSentEvent<Alarm>> subscribe(String receiverUuid) {
		return null;
	}
}
