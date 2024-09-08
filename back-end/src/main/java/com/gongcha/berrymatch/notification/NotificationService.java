package com.gongcha.berrymatch.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final Map<Long, SseEmitter> userSseEmitters = new ConcurrentHashMap<>();

    public SseEmitter createSseEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        userSseEmitters.put(userId, emitter);

        emitter.onCompletion(() -> userSseEmitters.remove(userId));
        emitter.onTimeout(() -> userSseEmitters.remove(userId));
        emitter.onError(throwable -> userSseEmitters.remove(userId));

        return emitter;
    }
}
