package com.gongcha.berrymatch.match.Service;

import com.gongcha.berrymatch.match.ThreadLocal.UserContext;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatchCompletionService {

    private static final Logger logger = LoggerFactory.getLogger(MatchCompletionService.class);
    private final WebClient webClient;
    private final UserRepository userRepository; // UserRepository 주입

    @Autowired
    public MatchCompletionService(WebClient.Builder webClientBuilder, UserRepository userRepository) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9000").build();
        this.userRepository = userRepository; // UserRepository 초기화
        logger.info("MatchCompletionService initialized with base URL: http://localhost:9000");
    }

    /**
     * 매칭 완료 요청을 외부 시스템으로 전송하는 메서드.
     * - 매칭된 유저들의 ID를 기반으로 데이터베이스에서 유저 정보를 조회합니다.
     * - 조회한 유저 정보를 기반으로 노드 서버로 데이터 전송을 수행합니다.
     */
    public void completeMatch(List<Long> matchedUserIds) {
        Long initiatingUserId = UserContext.getUserId();

        if (initiatingUserId != null && matchedUserIds.contains(initiatingUserId)) {
            // 유저 ID가 매칭된 유저 리스트에 포함되어 있는 경우에만 데이터를 전송
            List<User> matchedUsers = userRepository.findAllById(matchedUserIds);
            sendToNodeServer(matchedUsers, initiatingUserId);
        } else {
            logger.info("Initiating user ID not found in the matched users list. No data sent to Node server.");
        }
    }

    private void sendToNodeServer(List<User> matchedUsers, Long initiatingUserId) {
        try {
            // 노드 서버로 데이터 전송
            webClient.post()
                    .uri("/api/match/completed") // 노드 서버의 매칭 완료 API 엔드포인트
                    .body(BodyInserters.fromValue(createPayload(matchedUsers, initiatingUserId)))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe(
                            response -> logger.info("Successfully sent match data to Node server for user ID: " + initiatingUserId),
                            error -> logger.error("Failed to send match data to Node server: " + error.getMessage())
                    );
        } catch (Exception e) {
            logger.error("Error occurred while sending match data to Node server: " + e.getMessage());
        }
    }

    //노드로 담아서 보내줄 데이터 json으로 변환해서 보내줘야함
    // DTO를 사용안했음 수정필요할지도
    private Map<String, Object> createPayload(List<User> matchedUsers, Long initiatingUserId) {
        Map<String, Object> payload = new HashMap<>();
        List<Map<String, Object>> userInfos = new ArrayList<>();

        for (User user : matchedUsers) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("name", user.getNickname());
            // 필요한 추가 정보도 여기에 넣을 수 있습니다.
            userInfos.add(userInfo);
        }

        payload.put("matchedUsers", userInfos);
        payload.put("initiatingUserId", initiatingUserId);
        return payload;
    }
}
