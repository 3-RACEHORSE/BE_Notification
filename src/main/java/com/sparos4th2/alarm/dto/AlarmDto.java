package com.sparos4th2.alarm.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {

	private List<String> receiverUuids;
	private String message;
	private String eventType;
	private String alarmUrl;
}
