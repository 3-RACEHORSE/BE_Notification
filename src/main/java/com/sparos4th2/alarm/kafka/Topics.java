package com.sparos4th2.alarm.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topics {
    ALARM(Constant.ALARM)
    ;

    public static class Constant {
        public static final String ALARM ="alarm-topic";
    }

    private final String topic;
}
