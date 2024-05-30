package com.sparos4th2.alarm.presentation;

import com.sparos4th2.alarm.application.AlarmService;
import com.sparos4th2.alarm.application.AlarmServiceImpl;
import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.dto.AlarmRequestDto;
import com.sparos4th2.alarm.dto.AlarmResponseDto;
import com.sparos4th2.alarm.vo.AlarmVo;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/authorization/alarm")
@CrossOrigin(origins = "*")
public class AlarmController {

	private final AlarmService alarmService;

	@GetMapping(value = "/notifications")
	public Flux<Alarm> Notifications(@RequestHeader String uuid) {
		return alarmService.getAlarm(uuid);
	}

	//이벤트를 생성하는 메서드
	@GetMapping(value = "/send-notification")
	public void sendNotification() {
		alarmService.saveAlarm();
	}

	@GetMapping(value = "stream-notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<Object>> streamNotifications(@RequestHeader String uuid) {
		log.info("receiverUuid: {}", uuid);
		return alarmService.connect(uuid);
	}
}
