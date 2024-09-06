package com.gongcha.berrymatch.user.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonValue;
import com.gongcha.berrymatch.springSecurity.constants.ProviderInfo;
import com.gongcha.berrymatch.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupResponse {
    private String identifier;
    private Role role;
    private String providerInfo;

    @Builder
    public UserSignupResponse(String identifier, Role role, ProviderInfo providerInfo) {
        this.identifier = identifier;
        this.role = role;
        this.providerInfo = ProviderInfo.toString(providerInfo);
    }

}
