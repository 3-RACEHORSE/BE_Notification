package com.sparos4th2.alarm.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
public class NotificationDto {
    private String message;
    private String eventType;
    private String uuid;
    private LocalDateTime alarmTime;
    private String id;

    @Builder
    public NotificationDto(String message, String eventType,
                           String uuid, LocalDateTime alarmTime, String id) {
        this.message = message;
        this.eventType = eventType;
        this.uuid = uuid;
        this.alarmTime = alarmTime;
        this.id = id;
    }
}
