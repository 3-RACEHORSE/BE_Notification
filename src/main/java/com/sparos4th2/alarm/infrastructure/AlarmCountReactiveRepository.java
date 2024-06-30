package com.sparos4th2.alarm.infrastructure;

import com.sparos4th2.alarm.data.vo.StreamNotificationResponseVo;
import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.domain.AlarmCount;
import java.util.Optional;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface AlarmCountReactiveRepository extends ReactiveMongoRepository<AlarmCount, String> {

	@Tailable
	@Query("{ 'receiverUuid' : ?0 }")
	Flux<AlarmCount> findByReceiverUuid(String receiverUuid);

}
