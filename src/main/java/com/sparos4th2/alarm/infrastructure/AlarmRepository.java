package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.domain.Alarm;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AlarmRepository extends ReactiveMongoRepository<Alarm, String> {

	@Tailable
	@Query("{ 'receiver_uuid' : ?0 }")
	Flux<Alarm> findByReceiverUuid(String receiverUuid);
}
