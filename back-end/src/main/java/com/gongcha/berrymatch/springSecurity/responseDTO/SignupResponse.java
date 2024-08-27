package com.gongcha.berrymatch.springSecurity.responseDTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupResponse {
    Long userId;
    String username;

    public SignupResponse(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
