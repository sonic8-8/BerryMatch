package com.gongcha.berrymatch.chatMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository <ChatMessage,Long> {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.chatRoom.id = :chatRoomId ORDER BY cm.createdAt ASC")
    List<ChatMessage> findAllByChatRoomIdOrderByCreatedAtAsc(@Param("chatRoomId") Long chatRoomId);
}
