package com.sparos4th2.alarm.vo;

import lombok.Getter;

@Getter
public class AlarmRequestVo {

	private String receiverUuid;
	private String message;
	private String eventType;
}
