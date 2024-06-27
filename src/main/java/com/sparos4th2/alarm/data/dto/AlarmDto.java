package com.sparos4th2.alarm.data.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmDto {

	private List<String> receiverUuids;
	private String message;
	private String eventType;
	private String uuid;

	@Builder
	public AlarmDto(List<String> receiverUuids, String message, String eventType, String uuid) {
		this.receiverUuids = receiverUuids;
		this.message = message;
		this.eventType = eventType;
		this.uuid = uuid;
	}
}
