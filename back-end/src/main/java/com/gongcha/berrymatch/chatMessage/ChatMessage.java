package com.gongcha.berrymatch.chatMessage;

import com.gongcha.berrymatch.chatRoom.ChatRoom;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    private ChatRoom chatRoom;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public ChatMessage(ChatRoom chatRoom, User user, String message, LocalDateTime createdAt){
        this.chatRoom = chatRoom;
        this.user = user;
        this.message = message;
        this.createdAt = createdAt;
    }

}
