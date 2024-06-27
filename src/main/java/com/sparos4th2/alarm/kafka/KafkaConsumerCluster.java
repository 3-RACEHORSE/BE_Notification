package com.sparos4th2.alarm.kafka;

import com.sparos4th2.alarm.application.AlarmService;
import com.sparos4th2.alarm.data.dto.AlarmDto;

import java.util.LinkedHashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumerCluster {

	private final AlarmService alarmService;

	@KafkaListener(topics = Topics.Constant.ALARM, groupId = "alarm")
	public void consumeBidder(@Payload LinkedHashMap<String, Object> message,
		@Headers MessageHeaders messageHeaders) {
		log.info("consumer: success >>> message: {}, headers: {}", message.toString(),
			messageHeaders);

		//message를 PaymentReadyVo로 변환
		AlarmDto alarmDto = AlarmDto.builder()
			.receiverUuids((List<String>) message.get("receiverUuids"))
			.message(message.get("message").toString())
			.eventType(message.get("eventType").toString())
			.uuid(message.get("uuid").toString())
			.build();

		log.info("consumer: success >>> alarmDto: {}", alarmDto.toString());

		alarmService.consume(alarmDto);
	}
}
