package com.gongcha.berrymatch.chatMessage;

import com.gongcha.berrymatch.chatRoom.ChatRoom;
import com.gongcha.berrymatch.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageDTO {

    private ChatRoom chatRoom;
    private User user;
    private String message;
    private LocalDateTime createdAt;

    @Builder
    public ChatMessageDTO(ChatRoom chatRoom, User user, String message, LocalDateTime createdAt){
        this.chatRoom = chatRoom;
        this.user = user;
        this.message = message;
        this.createdAt = createdAt;
    }

    public ChatMessage toEntity(){
        return ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .message(message)
                .createdAt(createdAt)
                .build();

    }




}
