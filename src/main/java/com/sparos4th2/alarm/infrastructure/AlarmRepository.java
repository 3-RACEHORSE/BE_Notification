package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.domain.Alarm;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

public interface AlarmRepository extends ReactiveMongoRepository<Alarm, String> {
	
	@Query("{ 'receiverUuid' : ?0 }")
	Flux<Alarm> findAlarmByReceiverUuid(String receiverUuid);
}
