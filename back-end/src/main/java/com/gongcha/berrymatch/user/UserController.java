package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.user.RequestDTO.UserSignupRequest;
import com.gongcha.berrymatch.user.ResponseDTO.UserSignupResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @PostMapping("/signup")
    public ApiResponse<UserSignupResponse> registerUser(@RequestBody UserSignupRequest request) {

        // UserService 로직 ~~
        UserSignupResponse response = new UserSignupResponse();

        return ApiResponse.ok(response);
    }

}
