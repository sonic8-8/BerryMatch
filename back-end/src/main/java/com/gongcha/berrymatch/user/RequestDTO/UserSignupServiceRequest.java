package com.gongcha.berrymatch.user.RequestDTO;

import com.gongcha.berrymatch.user.City;
import com.gongcha.berrymatch.user.District;
import com.gongcha.berrymatch.user.Gender;

public class UserSignupServiceRequest {

    private String username;

    private String nickname;

    private String password;

    private City city;

    private District district;

    private Gender gender;

    private int age;

    private String phoneNumber;

    private String profileImageUrl;

    private String introduction;

    private String email;
}
