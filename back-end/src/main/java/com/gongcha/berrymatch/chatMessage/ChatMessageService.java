package com.gongcha.berrymatch.chatMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    // DB에 메세지 저장
    @Transactional
    public void saveMessage(ChatMessageDTO chatMessage) {
        ChatMessageDTO newChatMsg = ChatMessageDTO.builder()
                        .chatRoom(chatMessage.getChatRoom())
                        .message(chatMessage.getMessage())
                        .createdAt(chatMessage.getCreatedAt())
                        .user(chatMessage.getUser())
                                .build();
        chatMessageRepository.save(newChatMsg.toEntity());
    }

    // 해당하는 채팅방의 모든 채팅 내역 불러오기
    public List<ChatMessage> loadAllMessages(Long roomId) {
        return chatMessageRepository.findAllByChatRoomIdOrderByCreatedAtAsc(roomId);
    }

}
