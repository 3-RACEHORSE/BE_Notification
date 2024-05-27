package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.dto.AlarmResponseDto;
import com.sparos4th2.alarm.infrastructure.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AlarmServiceImpl implements AlarmService {

	private final AlarmRepository alarmRepository;

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
