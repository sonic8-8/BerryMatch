package com.gongcha.berrymatch.springSecurity.controller;


import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.springSecurity.domain.UserPrincipal;
import com.gongcha.berrymatch.springSecurity.requestDTO.TokenRequest;
import com.gongcha.berrymatch.springSecurity.responseDTO.AuthResponse;
import com.gongcha.berrymatch.springSecurity.responseDTO.LogoutResponse;
import com.gongcha.berrymatch.springSecurity.service.JwtFacade;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.gongcha.berrymatch.exception.ErrorCode.NOT_AUTHENTICATED_USER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final JwtFacade jwtFacade;

    @PostMapping("/auth")
    public ApiResponse<AuthResponse> generateToken(HttpServletResponse response,
                                             @RequestBody TokenRequest tokenRequest) {

        System.out.println("토큰 발행 요청 들어옴");

        User user = userService.findUserByIdentifier(tokenRequest.getIdentifier());

        if (!user.isRegistered()) {
            throw new BusinessException(NOT_AUTHENTICATED_USER);
        }

        jwtFacade.generateAccessToken(response, user);
        jwtFacade.generateRefreshToken(response, user);
        jwtFacade.setReissuedHeader(response);

        AuthResponse authResponse = userService.getUserAuthInfo(user.getIdentifier());

        System.out.println("토큰 발행함");

        return ApiResponse.ok(authResponse);
    }

    @PostMapping("/logout")
    public ApiResponse<LogoutResponse> logout(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletResponse response) {

        LogoutResponse result = LogoutResponse.builder()
                .message(jwtFacade.logout(response, userPrincipal.getUser().getIdentifier()))
                .build();

        System.out.println("와이라노 성공인데");

        return ApiResponse.ok(result);
    }



}
