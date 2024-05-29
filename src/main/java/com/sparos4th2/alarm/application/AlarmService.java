package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.dto.AlarmRequestDto;
import com.sparos4th2.alarm.dto.AlarmResponseDto;
import com.sparos4th2.alarm.vo.AlarmVo;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlarmService {

	void saveAlarm();

	Flux<Alarm> getAlarm(String receiverUuid);

	Flux<Alarm> streamAlarms(String receiverUuid);
}