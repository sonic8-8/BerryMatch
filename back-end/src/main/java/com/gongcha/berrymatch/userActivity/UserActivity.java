package com.gongcha.berrymatch.userActivity;

import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private LogType logType;

    private LocalDateTime logTime;

}
