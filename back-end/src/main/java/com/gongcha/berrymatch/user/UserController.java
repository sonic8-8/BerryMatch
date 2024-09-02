package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.user.RequestDTO.UserSignupRequest;
import com.gongcha.berrymatch.user.ResponseDTO.UserSignupResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 소셜 로그인으로 받아온 identifier + 사용자 입력으로 requestDTO를 만들고 회원가입 시켜주는 메서드
     */
    @PostMapping("/auth/signup")
    public ApiResponse<UserSignupResponse> signup(@RequestBody UserSignupRequest userSignupRequest) {

        return ApiResponse.ok(userService.signup(userSignupRequest.toService()));
    }

    @GetMapping("/user-info")
    public ApiResponse<User> getUserInfo(@RequestParam String identifier) {
        System.out.println("요청들어옴");
        return ApiResponse.ok(userService.findUserByIdentifier(identifier));
    }


}
