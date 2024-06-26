package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.data.vo.NotificationResponseVo;
import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.data.dto.AlarmDto;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface AlarmService {

	void saveAlarm();

	NotificationResponseVo getAlarm(String receiverUuid, Integer page, Integer size);

	void finish(String receiverUuid);

	void consume(AlarmDto alarmDto);
}