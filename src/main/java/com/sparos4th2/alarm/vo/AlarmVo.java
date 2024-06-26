package com.sparos4th2.alarm.vo;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AlarmVo {

	private String receiverUuid;
	private String message;
	private String eventType;
	private LocalDateTime alarmTime;
	private int alarmCount;

	@Builder
	public AlarmVo(String receiverUuid, String message, String eventType, LocalDateTime alarmTime,
		int alarmCount) {
		this.receiverUuid = receiverUuid;
		this.message = message;
		this.eventType = eventType;
		this.alarmTime = alarmTime;
		this.alarmCount = alarmCount;
	}
}
