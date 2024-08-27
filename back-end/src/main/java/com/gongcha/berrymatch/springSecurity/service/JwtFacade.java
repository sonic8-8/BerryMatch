package com.gongcha.berrymatch.springSecurity.service;


import com.gongcha.berrymatch.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface JwtFacade {
    String generateAccessToken(HttpServletResponse response, User user);

    String generateRefreshToken(HttpServletResponse response, User user);

    String resolveAccessToken(HttpServletRequest request);

    String resolveRefreshToken(HttpServletRequest request);

    String getIdentifierFromRefresh(String refreshToken);

    boolean validateAccessToken(String accessToken);

    boolean validateRefreshToken(String refreshToken, String username);

    void setReissuedHeader(HttpServletResponse response);

    String logout(HttpServletResponse response, String username);

    Authentication getAuthentication(String accessToken);
}
