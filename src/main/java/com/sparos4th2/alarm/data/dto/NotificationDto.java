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
    private String alarmUrl;
    private LocalDateTime alarmTime;

    @Builder
    public NotificationDto(String message, String eventType, String alarmUrl, LocalDateTime alarmTime) {
        this.message = message;
        this.eventType = eventType;
        this.alarmUrl = alarmUrl;
        this.alarmTime = alarmTime;
    }
}
