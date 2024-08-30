package com.gongcha.berrymatch.springSecurity.requestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequest {
    private String identifier;

    @Builder
    public TokenRequest(String identifier,  String provider) {
        this.identifier = identifier;
    }
}
