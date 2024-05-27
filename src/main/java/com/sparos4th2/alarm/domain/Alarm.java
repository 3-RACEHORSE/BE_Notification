package com.sparos4th2.alarm.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "alarm")
public class Alarm {

	@Id
	private String id;
	private String receiverUuid;
	private String message;
	private String eventType;
	private LocalDateTime alarmTime;

	@Builder
	public Alarm(String receiverUuid, String message, String eventType, LocalDateTime alarmTime) {
		this.receiverUuid = receiverUuid;
		this.message = message;
		this.eventType = eventType;
		this.alarmTime = alarmTime;
	}
}
