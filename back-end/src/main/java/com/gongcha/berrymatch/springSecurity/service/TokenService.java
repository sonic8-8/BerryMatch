package com.gongcha.berrymatch.springSecurity.service;


import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.exception.ErrorCode;
import com.gongcha.berrymatch.springSecurity.constants.ProviderInfo;
import com.gongcha.berrymatch.springSecurity.domain.Token;
import com.gongcha.berrymatch.springSecurity.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public String save(Token token) {
        Token savedToken = tokenRepository.save(token);
        return savedToken.getIdentifier();
    }

    public Token findByIdentifierAndProviderInfo(String identifier, ProviderInfo providerInfo) {
        return tokenRepository.findByIdentifierAndProviderInfo(identifier, providerInfo)
                .orElseThrow(() -> new BusinessException(ErrorCode.JWT_NOT_FOUND_IN_DB));
    }

    public boolean isRefreshHijacked(String identifier, String refreshToken, ProviderInfo providerInfo) {
        Token token = findByIdentifierAndProviderInfo(identifier, providerInfo);
        return !token.getToken().equals(refreshToken);
    }

    public void deleteByIdAndProviderInfo(String identifier, ProviderInfo providerInfo) {
        tokenRepository.deleteByIdentifierAndProviderInfo(identifier, providerInfo);
    }
}
