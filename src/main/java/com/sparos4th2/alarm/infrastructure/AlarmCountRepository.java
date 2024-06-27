package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.domain.AlarmCount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmCountRepository extends MongoRepository<AlarmCount, String> {
    Optional<AlarmCount> findByReceiverUuid(String receiverUuid);

    List<AlarmCount> findAllByReceiverUuid(String uuid);
}
