package com.sparos4th2.alarm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Document(collection = "alarm_count")
@NoArgsConstructor
@ToString
public class AlarmCount {

	@Id
	private String id;
	private String receiverUuid;
	private int alarmCount;
	private LocalDateTime alarmTime;

	@Builder
	public AlarmCount(String receiverUuid, int alarmCount, LocalDateTime alarmTime) {
		this.receiverUuid = receiverUuid;
		this.alarmCount = alarmCount;
		this.alarmTime = alarmTime;
	}
}
