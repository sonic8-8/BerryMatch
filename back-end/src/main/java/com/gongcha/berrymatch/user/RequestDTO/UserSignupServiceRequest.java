package com.gongcha.berrymatch.user.RequestDTO;

import com.gongcha.berrymatch.user.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignupServiceRequest {

    private String identifier;
    private String nickname;
    private City city;
    private District district;
    private Gender gender;
    private int age;
    private String phoneNumber;
    private String profileImageUrl;
    private String introduction;

    @Builder
    public UserSignupServiceRequest(String identifier, String nickname, City city, District district, Gender gender, int age, String phoneNumber, String profileImageUrl, String introduction) {
        this.identifier = identifier;
        this.nickname = nickname;
        this.city = city;
        this.district = district;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.introduction = introduction;
    }

    public User toEntity() {
        return User.builder()
                .identifier(identifier)
                .nickname(nickname)
                .city(city)
                .district(district)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .profileImageUrl(profileImageUrl)
                .introduction(introduction)
                .build();
    }
}
