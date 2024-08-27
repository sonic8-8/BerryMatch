package com.gongcha.berrymatch.springSecurity.responseDTO;

import com.gongcha.berrymatch.user.Role;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthResponse {
    private Role role;

    @Builder
    public AuthResponse(Role role) {
        this.role = role;
    }
}


