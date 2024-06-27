package com.sparos4th2.alarm.data.vo;

import com.sparos4th2.alarm.data.dto.NotificationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class NotificationResponseVo {
    private List<NotificationDto> notificationDtoList;

    @Builder
    public NotificationResponseVo(List<NotificationDto> notificationDtoList) {
        this.notificationDtoList = notificationDtoList;
    }
}
