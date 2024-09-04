package com.gongcha.berrymatch.springSecurity.responseDTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SignupResponse {
    Long userId;
    String identifier;

    public SignupResponse(Long userId, String identifier) {
        this.userId = userId;
        this.identifier = identifier;
    }
}
