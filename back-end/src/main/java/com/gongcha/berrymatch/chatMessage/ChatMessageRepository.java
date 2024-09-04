package com.gongcha.berrymatch.chatMessage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository <ChatMessage,Long> {
    List<ChatMessage> findAllByChatRoomOrderByCreatedAtAsc(Long id);
}
