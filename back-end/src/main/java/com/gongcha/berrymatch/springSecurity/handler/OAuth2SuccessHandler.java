package com.gongcha.berrymatch.springSecurity.handler;

import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.exception.ErrorCode;
import com.gongcha.berrymatch.user.Role;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final String SIGNUP_URL;
    private final String AUTH_URL;
    private final UserRepository userRepository;

    public OAuth2SuccessHandler(@Value("${url.base}") String BASE_URL,
                                @Value("${url.path.signup}") String SIGN_UP_PATH,
                                @Value("${url.path.auth}") String AUTH_PATH,
                                UserRepository userRepository) {
        this.userRepository = userRepository;
        this.SIGNUP_URL = BASE_URL + SIGN_UP_PATH;
        this.AUTH_URL = BASE_URL + AUTH_PATH;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String username = oAuth2User.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        String redirectUrl = getRedirectUrlByRole(user.getRole(), username);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String getRedirectUrlByRole(Role role, String identifier) {
        if (role == Role.NOT_REGISTERED) {
            return UriComponentsBuilder.fromUriString(SIGNUP_URL)
                    .queryParam("identifier", identifier)
                    .build()
                    .toUriString();
        }

        return UriComponentsBuilder.fromHttpUrl(AUTH_URL)
                .queryParam("identifier", identifier)
                .build()
                .toUriString();
    }
}
