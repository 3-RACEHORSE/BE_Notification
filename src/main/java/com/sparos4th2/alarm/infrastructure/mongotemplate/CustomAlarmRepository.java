package com.sparos4th2.alarm.infrastructure.mongotemplate;

import com.sparos4th2.alarm.domain.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface CustomAlarmRepository {
    Page<Alarm> findAllAlarm(String receiverUuid, Pageable pageable);
}
