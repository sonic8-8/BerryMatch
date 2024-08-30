package com.gongcha.berrymatch.user.RequestDTO;

import com.gongcha.berrymatch.user.City;
import com.gongcha.berrymatch.user.District;
import com.gongcha.berrymatch.user.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSignupRequest {

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
    public UserSignupRequest(String identifier, String nickname, City city, District district, Gender gender, int age, String phoneNumber, String profileImageUrl, String introduction) {
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

    public UserSignupServiceRequest toService() {
        return UserSignupServiceRequest.builder()
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
