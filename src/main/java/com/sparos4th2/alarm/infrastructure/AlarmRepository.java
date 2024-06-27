package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.domain.Alarm;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlarmRepository extends MongoRepository<Alarm, String> {
    List<Alarm> findAllByReceiverUuidOrderByAlarmTimeDesc(String receiverUuid);
}
