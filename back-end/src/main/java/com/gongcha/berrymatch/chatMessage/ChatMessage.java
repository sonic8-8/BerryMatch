package com.gongcha.berrymatch.chatMessage;

import com.gongcha.berrymatch.chatRoom.ChatRoom;
import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private ChatRoom chatRoom;

    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
