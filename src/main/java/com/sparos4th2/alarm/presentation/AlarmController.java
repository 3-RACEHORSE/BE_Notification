package com.sparos4th2.alarm.presentation;

import com.sparos4th2.alarm.application.AlarmService;
import com.sparos4th2.alarm.common.SuccessResponse;
import com.sparos4th2.alarm.data.vo.NotificationResponseVo;
import com.sparos4th2.alarm.data.vo.StreamNotificationResponseVo;
import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.domain.AlarmCount;
import com.sparos4th2.alarm.infrastructure.AlarmCountReactiveRepository;
import com.sparos4th2.alarm.infrastructure.AlarmCountRepository;
import com.sparos4th2.alarm.infrastructure.AlarmRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/alarm")
@CrossOrigin(origins = "*")
@Tag(name = "알람", description = "알람 API")
public class AlarmController {

	private final AlarmService alarmService;
	private final AlarmCountReactiveRepository alarmCountReactiveRepository;
	private final AlarmCountRepository alarmCountRepository;
	private final AlarmRepository alarmRepository;

	//알림 조회
	@GetMapping(value = "/notifications")
	@Operation(summary = "알림 조회", description = "알림을 조회합니다.")
	public SuccessResponse<NotificationResponseVo> Notifications(
			@RequestHeader String uuid) {
		return new SuccessResponse<>(alarmService.getAlarm(uuid));
	}

	//이벤트를 생성하는 메서드
	@GetMapping(value = "/send-notification")
	@Operation(summary = "알림 생성(test용으로만 써주세요)", description = "알림을 생성합니다.")
	public void sendNotification() {
		alarmService.saveAlarm();
	}

	//알림 SSE연결요청
	@GetMapping(value = "/stream-notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@Operation(summary = "미확인 알림 개수 SSE연결", description = "읽지않은 알림을 실시간으로 받습니다.")
	public Flux<StreamNotificationResponseVo> streamNotifications(@RequestHeader String uuid) {
		return alarmCountRepository.findFirstByReceiverUuidOrderByAlarmTimeDesc(uuid)
				.map(Mono::just)
				.orElseGet(() -> {
					AlarmCount initialAlarmCount = AlarmCount.builder()
							.alarmCount(0)
							.receiverUuid(uuid)
							.alarmTime(LocalDateTime.now())
							.build();
					alarmCountRepository.save(initialAlarmCount);
					return Mono.just(initialAlarmCount);
				})
				.flatMapMany(initialAlarmCount ->
						Flux.concat(
								Mono.just(new StreamNotificationResponseVo(initialAlarmCount.getAlarmCount())),
								alarmCountReactiveRepository.findByReceiverUuid(uuid)
										.skip(Duration.ofSeconds(1))
										.map(alarmCount -> new StreamNotificationResponseVo(alarmCount.getAlarmCount()))
										.subscribeOn(Schedulers.boundedElastic())
						)
				);
	}

	// 알림 삭제
	@DeleteMapping(value = "/delete/{id}")
	@Operation(summary = "알림 삭제 API", description = "알림 id를 통해 알림 완전 삭제")
	public SuccessResponse<Object> deleteAlarm(
			@PathVariable("id") String id) {
		alarmRepository.deleteById(id);
		return new SuccessResponse<>(null);
	}
}
