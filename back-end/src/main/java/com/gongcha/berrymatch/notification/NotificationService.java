package com.gongcha.berrymatch.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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


    /**
     * 알림 기능을 위한 SSE 방출기를 생성해주는 메서드
     */
    public SseEmitter createSseEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        userEmitters.put(userId, emitter);

        emitter.onCompletion(() -> userEmitters.remove(userId));
        emitter.onTimeout(() -> userEmitters.remove(userId));
        emitter.onError(throwable -> userEmitters.remove(userId));

        return emitter;
    }

    public void sendNotification(Long userId) {
        Notification notification = Notification.builder()
                .message("알림임")
                .id(userId)
                .build();

        emitNotification(notificationRepository.save(notification));
    }

    public void emitNotification(Notification notification) {
        SseEmitter emitter = userEmitters.get(notification.getId());
        if (emitter != null) {
            executor.execute(() -> {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(notification.getMessage()));
                } catch (Exception e) {
                    emitter.completeWithError(e);
                    userEmitters.remove(notification.getId());

                }
            });
        }
    }

}
