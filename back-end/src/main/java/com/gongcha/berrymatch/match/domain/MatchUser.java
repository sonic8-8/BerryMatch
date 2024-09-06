package com.gongcha.berrymatch.match.domain;

import com.gongcha.berrymatch.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //경기 준비 상태
    @Enumerated(EnumType.STRING)
    private MatchUserReady status = MatchUserReady.Waiting;

    //A/B팀 상태
    @Enumerated(EnumType.STRING)
    private MatchTeam team;


    //락킹
    @Version // Optimistic locking field
    private Long version;



}