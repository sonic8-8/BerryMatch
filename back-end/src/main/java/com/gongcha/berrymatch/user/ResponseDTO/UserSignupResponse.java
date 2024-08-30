package com.gongcha.berrymatch.user.ResponseDTO;

import com.gongcha.berrymatch.user.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignupResponse {
    private String identifier;
    private Role role;

    @Builder
    public UserSignupResponse(String identifier, Role role) {
        this.identifier = identifier;
        this.role = role;
    }
}
