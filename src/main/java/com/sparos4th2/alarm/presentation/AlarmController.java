package com.sparos4th2.alarm.presentation;

import com.sparos4th2.alarm.application.AlarmService;
import com.sparos4th2.alarm.common.SuccessResponse;
import com.sparos4th2.alarm.data.vo.NotificationResponseVo;
import com.sparos4th2.alarm.data.vo.StreamNotificationResponseVo;
import com.sparos4th2.alarm.infrastructure.AlarmCountReactiveRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/alarm")
@CrossOrigin(origins = "*")
@Tag(name = "알람", description = "알람 API")
public class AlarmController {

	private final AlarmService alarmService;
	private final AlarmCountReactiveRepository alarmCountReactiveRepository;

	//알림 조회
	@GetMapping(value = "/notifications")
	@Operation(summary = "알림 조회", description = "알림을 조회합니다.")
	public SuccessResponse<NotificationResponseVo> Notifications(
			@RequestHeader String uuid,
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer size) {
		return new SuccessResponse<>(alarmService.getAlarm(uuid, page, size));
	}

	//이벤트를 생성하는 메서드
	@GetMapping(value = "/send-notification")
	@Operation(summary = "알림 생성(test용으로만 써주세요)", description = "알림을 생성합니다.")
	public void sendNotification() {
		alarmService.saveAlarm();
	}

	//알림 SSE연결요청
	@GetMapping(value = "stream-notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@Operation(summary = "미확인 알림 개수 SSE연결", description = "읽지않은 알림을 실시간으로 받습니다.")
	public Flux<StreamNotificationResponseVo> streamNotifications(@RequestHeader String uuid) {
		return alarmCountReactiveRepository.findByReceiverUuid(uuid)
				.subscribeOn(Schedulers.boundedElastic());
	}

	@GetMapping(value = "/finish")
	@Operation(summary = "알림 연결 종료", description = "알림을 연결을 종료합니다.")
	public void finish(@RequestHeader String uuid) {
		alarmService.finish(uuid);
	}
}
