package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.infrastructure.mongotemplate.CustomAlarmRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlarmRepository extends MongoRepository<Alarm, String>, CustomAlarmRepository {
}
