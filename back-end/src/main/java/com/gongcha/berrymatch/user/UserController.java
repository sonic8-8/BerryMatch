package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.ApiResponse;
import com.gongcha.berrymatch.postFile.requestDTO.PostFileUploadRequest;
import com.gongcha.berrymatch.s3bucket.S3Service;
import com.gongcha.berrymatch.springSecurity.constants.ProviderInfo;
import com.gongcha.berrymatch.user.RequestDTO.UserSignupRequest;
import com.gongcha.berrymatch.user.ResponseDTO.UserProfileUpdateResponse;
import com.gongcha.berrymatch.user.ResponseDTO.UserSignupResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;

    /**
     * 소셜 로그인으로 받아온 identifier + 사용자 입력으로 requestDTO를 만들고 회원가입 시켜주는 메서드
     */
    @PostMapping("/auth/signup")
    public ApiResponse<UserSignupResponse> signup(@RequestBody UserSignupRequest userSignupRequest) {

        System.out.println("회원가입 요청 들어옴");

        System.out.println(userSignupRequest.getProviderInfo());
        System.out.println("여기는 providerInfo 정상적으로 들어옴");

        return ApiResponse.ok(userService.signup(userSignupRequest.toService()));
    }

    @GetMapping("/user-info")
    public ApiResponse<User> getUserInfo(@RequestParam("identifier") String identifier, @RequestParam("providerInfo") String providerInfo) {
        System.out.println("요청들어옴");
        return ApiResponse.ok(userService.findUserByOAuthInfo(identifier, ProviderInfo.valueOf(providerInfo.toUpperCase())));
    }

    @PostMapping("/profile/update")
    public ApiResponse<UserProfileUpdateResponse> updateUserInfo(@RequestParam("file") MultipartFile file,
                                                                 @RequestParam("identifier") String identifier,
                                                                 @RequestParam("providerInfo") String providerInfo,
                                                                 @RequestParam("introduction") String introduction
                                                ) throws IOException {


            PostFileUploadRequest request = PostFileUploadRequest.of(file);
            String key = s3Service.uploadProfileImage(request.toServiceRequest());

        return ApiResponse.ok(userService.updateProfile(identifier,
                ProviderInfo.valueOf(providerInfo.toUpperCase()),
                key, introduction));
    }


}
