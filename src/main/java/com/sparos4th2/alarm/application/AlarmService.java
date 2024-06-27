package com.sparos4th2.alarm.application;

import com.sparos4th2.alarm.data.vo.NotificationResponseVo;
import com.sparos4th2.alarm.data.dto.AlarmDto;

public interface AlarmService {

	void saveAlarm();

	NotificationResponseVo getAlarm(String receiverUuid);

	void consume(AlarmDto alarmDto);
}