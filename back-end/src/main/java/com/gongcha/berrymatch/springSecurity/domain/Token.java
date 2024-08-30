package com.gongcha.berrymatch.springSecurity.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "Token")
@NoArgsConstructor
public class Token {
    @Id
    private String identifier;

    private String token;

    @Builder
    public Token(String identifier, String token) {
        this.identifier = identifier;
        this.token = token;
    }
}
