package com.gongcha.berrymatch.notification;

import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.exception.ErrorCode;
import com.gongcha.berrymatch.notification.responseDTO.NotificationResponse;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Map<Long, SseEmitter> userEmitters = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final UserRepository userRepository;


    /**
     * 알림 기능을 위한 SSE 방출기를 생성해주는 메서드
     */
    public SseEmitter createSseEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        userEmitters.put(userId, emitter);

        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));
        emitter.onError(throwable -> userEmitters.remove(userId));

        // 초기 이벤트 전송
        try {
            emitter.send(SseEmitter.event().data("연결 성공"));
            sendMatchStatus(userId);
        } catch (IOException e) {
            userEmitters.remove(userId, emitter);
        }

        return emitter;
    }

    /**
     * 테스트용 알림을 보내는 메서드
     */
    public NotificationResponse sendNotification(Long userId) {
        SseEmitter emitter = userEmitters.get(userId);
        Notification notification = null;

        try {
            notification = Notification.builder()
                    .message("알림임")
                    .id(userId)
                    .build();

            emitter.send(SseEmitter.event().name("notification").data(notification.getMessage()));
        } catch (IOException e) {
            emitter.completeWithError(e);
            userEmitters.remove(userId);
        }

        return NotificationResponse.builder()
                .message(notification.getMessage())
                .build();
    }

    /**
     * User의 matchStatus를 조회 후 알림 보내주는 메서드 <br/>
     * 사용법 : matchStatus가 변하는 로직이 있다면 이 메서드를 넣으십쇼. <br/>
     * ex) User의 MatchStatus를 업데이트하는 로직은 아마 UserService에 있겠죠 <br/>
     * 예를 들어, UserService.updateMatchStatus 같은게 있을듯요. <br/>
     * 그 메서드 안쪽에 notificationService.sendMatchStatus()를 추가하셈
     */
    public NotificationResponse sendMatchStatus(Long userId) {
        SseEmitter emitter = userEmitters.get(userId);
        Notification notification = null;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        try {
            notification = Notification.builder()
                    .message(user.getUserMatchStatus().toString())
                    .id(userId)
                    .build();

            emitter.send(SseEmitter.event().name("matchStatus").data(notification.getMessage()));
        } catch (IOException e) {
            emitter.completeWithError(e);
            userEmitters.remove(userId);
        }

        return NotificationResponse.builder()
                .message(notification.getMessage())
                .build();
    }


}
