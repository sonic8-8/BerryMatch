package com.gongcha.berrymatch.springSecurity.requestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequest {
    private String username;

    @Builder
    public TokenRequest(String username) {
        this.username = username;
    }
}
