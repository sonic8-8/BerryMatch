package com.gongcha.berrymatch.springSecurity.repository;

import com.gongcha.berrymatch.springSecurity.domain.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {
    Token findByIdentifier(String identifier);
}
