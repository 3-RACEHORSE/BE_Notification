package com.sparos4th2.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRequestDto {

	private String receiverUuid;
	private String message;
	private String eventType;
}
