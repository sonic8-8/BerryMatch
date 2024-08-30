package com.gongcha.berrymatch.springSecurity.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ProviderInfo {

    KAKAO("kakao_account", "id", "email"),
    NAVER("response", "id", "email"),
    GOOGLE(null, "sub", "email");

    private final String attributeKey;
    private final String providerCode;
    private final String Identifier;

    public static ProviderInfo from(String provider) {
        String upperCastedProvider = provider.toUpperCase();

        return Arrays.stream(ProviderInfo.values())
                .filter(item -> item.name().equals(upperCastedProvider))
                .findFirst()
                .orElseThrow();
    }
}
