package com.gongcha.berrymatch.springSecurity.filter;


import com.gongcha.berrymatch.springSecurity.constants.ProviderInfo;
import com.gongcha.berrymatch.springSecurity.service.JwtFacade;
import com.gongcha.berrymatch.user.User;
import com.gongcha.berrymatch.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.gongcha.berrymatch.springSecurity.config.SecurityConfig.PERMITTED_URI;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtFacade jwtFacade;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isPermittedURI(request.getRequestURI())) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtFacade.resolveAccessToken(request);
        if (jwtFacade.validateAccessToken(accessToken)) {
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtFacade.resolveRefreshToken(request);
        User user = findUserByRefreshToken(refreshToken);

        if (jwtFacade.validateRefreshToken(refreshToken, user.getIdentifier(), user.getProviderInfo())) {
            String reissuedAccessToken = jwtFacade.generateAccessToken(response, user);
            jwtFacade.generateRefreshToken(response, user);
            jwtFacade.setReissuedHeader(response);

            setAuthenticationToContext(reissuedAccessToken);
            filterChain.doFilter(request, response);
            return;
        }

        jwtFacade.logout(response, user.getIdentifier(), user.getProviderInfo());
    }

    private boolean isPermittedURI(String requestURI) {
        return Arrays.stream(PERMITTED_URI)
                .anyMatch(permitted -> {
                    String replace = permitted.replace("*", "");
                    return requestURI.contains(replace) || replace.contains(requestURI);
                });
    }

    private User findUserByRefreshToken(String refreshToken) {
        String identifier = jwtFacade.getIdentifierFromRefresh(refreshToken);
        ProviderInfo providerInfo = jwtFacade.getProviderInfoFromRefresh(refreshToken);
        return userService.findUserByOAuthInfo(identifier, providerInfo);
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtFacade.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
