package com.sparos4th2.alarm.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "alarm")
public class Alarm {

	@Id
	private String id;
	private String receiverUuid;
	private String message;
	private String eventType;
	private String uuid;
	private LocalDateTime alarmTime;

	@Builder
	public Alarm(String receiverUuid, String message, String eventType, String uuid, LocalDateTime alarmTime) {
		this.receiverUuid = receiverUuid;
		this.message = message;
		this.eventType = eventType;
		this.uuid = uuid;
		this.alarmTime = alarmTime;
	}
}
