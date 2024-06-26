package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.domain.AlarmCount;
import java.util.Optional;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlarmCountRepository extends ReactiveMongoRepository<AlarmCount, String> {

	@Query("{ 'receiverUuid' : ?0 }")
	AlarmCount findByReceiverUuid(String receiverUuid);

	@Query("{ 'receiverUuid' : ?0 }")
	Optional<Alarm> existsByReceiverUuid(String receiverUuid);
}
