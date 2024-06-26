package com.sparos4th2.alarm.data.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmDto {

	private List<String> receiverUuids;
	private String message;
	private String eventType;

	@Builder
	public AlarmDto(List<String> receiverUuids, String message, String eventType) {
		this.receiverUuids = receiverUuids;
		this.message = message;
		this.eventType = eventType;
	}
}
