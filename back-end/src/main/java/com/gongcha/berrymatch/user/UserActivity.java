package com.gongcha.berrymatch.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

}
