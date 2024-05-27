package com.sparos4th2.alarm.presentation;

import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/authorization/alarm")
public class AlarmController {

	private final Sinks.Many<String> sink;

	public AlarmController() {
		this.sink = Sinks.many().multicast().onBackpressureBuffer();
	}

	@GetMapping(path = "/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamNotifications(@RequestHeader String uuid) {
		return sink.asFlux().map(data -> "data: " + data + "\n\n");
	}

	// 예시로 매 5초마다 이벤트를 생성하는 메서드
	@GetMapping("/send-notification")
	public void sendNotification() {
		log.info("Notification at " + LocalTime.now());
		sink.tryEmitNext("Notification at " + LocalTime.now());
	}
}
