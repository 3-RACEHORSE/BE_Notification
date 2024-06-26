package com.sparos4th2.alarm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "alarm_count")
@NoArgsConstructor
public class AlarmCount {

	@Id
	private String id;
	private String receiverUuid;
	private int alarmCount;

	@Builder
	public AlarmCount(String receiverUuid, int alarmCount) {
		this.receiverUuid = receiverUuid;
		this.alarmCount = alarmCount;
	}
}
