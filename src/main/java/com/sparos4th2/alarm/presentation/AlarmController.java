package com.sparos4th2.alarm.presentation;

import com.sparos4th2.alarm.application.AlarmService;
import com.sparos4th2.alarm.domain.Alarm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/alarm")
@CrossOrigin(origins = "*")
@Tag(name = "알람", description = "알람 API")
public class AlarmController {

	private final AlarmService alarmService;

	//알림 조회
	@GetMapping(value = "/notifications")
	@Operation(summary = "알림 조회", description = "알림을 조회합니다.")
	public Flux<Alarm> Notifications(@RequestHeader String uuid) {
		return alarmService.getAlarm(uuid);
	}

	//이벤트를 생성하는 메서드
	@GetMapping(value = "/send-notification")
	@Operation(summary = "알림 생성(test용으로만 써주세요)", description = "알림을 생성합니다.")
	public void sendNotification() {
		alarmService.saveAlarm();
	}

	//알림 SSE연결요청
	@GetMapping(value = "stream-notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@Operation(summary = "알림 SSE연결", description = "알림을 실시간으로 받습니다.")
	public Flux<ServerSentEvent<Object>> streamNotifications(@RequestHeader String uuid) {
		log.info("receiverUuid: {}", uuid);
		return alarmService.connect(uuid);
	}

	@GetMapping(value = "/finish")
	@Operation(summary = "알림 연결 종료", description = "알림을 연결을 종료합니다.")
	public void finish(@RequestHeader String uuid) {
		alarmService.finish(uuid);
	}
}
