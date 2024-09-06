package com.gongcha.berrymatch.chatMessage;

import com.gongcha.berrymatch.ApiResponse;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    /**
     * DB에 메세지 내용 저장
     * @param chatMessage
     * @return String
     */
    @PostMapping("/chat/save-msg")
    public ApiResponse<String> saveMessage(@RequestBody ChatMessageDTO chatMessage){
        chatMessageService.saveMessage(chatMessage);
        return ApiResponse.ok("Message saved successfully");
    }

    /**
     * 해당하는 방의 모든 채팅 내역 불러와서 보내주기
     * @param roomId
     * @return List<ChatMessage>
     */
    @GetMapping("/chat/{roomId}")
    public ApiResponse<List<ChatMessage>> loadALlMessages(@PathVariable Long roomId){
        return ApiResponse.ok(chatMessageService.loadAllMessages(roomId));
    }


}
