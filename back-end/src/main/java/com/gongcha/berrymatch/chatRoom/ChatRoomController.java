package com.gongcha.berrymatch.chatRoom;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.chatMessage.ChatMessage;
import com.gongcha.berrymatch.chatMessage.ChatMessageDTO;
import com.gongcha.berrymatch.chatMessage.ChatMessageService;
import com.gongcha.berrymatch.match.domain.MatchingQueue;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    /**
     * 매칭 완료된 사용자들의 채팅방에 대한 Data DB에 등록
     */
    @PostMapping("/chatRoom")
    public ApiResponse<Long> createNewChatRoom(@RequestBody ChatRoomDTO chatRoom){
        ChatRoom newChatRoom = chatRoomService.transferMatchResultsToChat(chatRoom);
        return ApiResponse.ok(newChatRoom.getId());
    }

    /**
     * 해당 채팅방의 정보를 보내줌
     */
    @GetMapping("/chatRoom/{roomId}/info")
    public ApiResponse<ChatRoom> loadChatRoomInfo(@PathVariable Long roomId){
        return ApiResponse.ok(chatRoomService.loadChatRoomInfo(roomId));
    }

    /**
     * DB에서 해당하는 채팅방의 메세지 내용 불러오기
     */
    @GetMapping("/chatRoom/{roomId}/msgs")
    public ApiResponse<List<ChatMessage>> loadMessages(@PathVariable Long roomId){
        return ApiResponse.ok(chatMessageService.loadAllMessages(roomId));
    }

}
