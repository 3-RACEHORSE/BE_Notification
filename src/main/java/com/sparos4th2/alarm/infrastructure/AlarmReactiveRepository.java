package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.domain.Alarm;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AlarmReactiveRepository extends ReactiveMongoRepository<Alarm, String> {
	
	@Query("{ 'receiverUuid' : ?0 }")
	Flux<Alarm> findAlarmByReceiverUuid(String receiverUuid);
}
