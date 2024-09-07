package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.springSecurity.constants.ProviderInfo;
import com.gongcha.berrymatch.springSecurity.responseDTO.AuthResponse;
import com.gongcha.berrymatch.user.RequestDTO.UserSignupServiceRequest;
import com.gongcha.berrymatch.user.ResponseDTO.UserProfileUpdateResponse;
import com.gongcha.berrymatch.user.ResponseDTO.UserSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gongcha.berrymatch.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Value("${AMAZON_S3_BUCKET_NAME}")
    private String bucketName;
    @Value("${AMAZON_S3_BUCKET_REGION}")
    private String region;


//    private final UserDetailsServiceAutoConfiguration userDetailsServiceAutoConfiguration;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    public User findUserByIdentifier(String identifier, ProviderInfo providerInfo) {
        return userRepository.findByOAuthInfo(identifier, providerInfo)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    public AuthResponse getUserAuthInfo(String identifier, ProviderInfo providerInfo) {
        User user = userRepository.findByOAuthInfo(identifier, providerInfo)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
        return new AuthResponse(user.getRole());
    }

    public User findUserByOAuthInfo(String identifier, ProviderInfo providerInfo) {
        return userRepository.findByOAuthInfo(identifier, providerInfo)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    /**
     * FE로 받은 사용자 입력으로 User 정보를 업데이트 해 회원가입 시켜주는 메서드
     */
    @Transactional
    public UserSignupResponse signup(UserSignupServiceRequest request) {

        System.out.println("identifier" + request.getIdentifier());
        System.out.println("providerInfo" + request.getProviderInfo());

        User user = userRepository.findByOAuthInfo(request.getIdentifier(), request.getProviderInfo())
                        .orElseThrow(() -> new BusinessException(NOT_AUTHENTICATED_USER));

        user.update(request);

        User savedUser = userRepository.save(user);

        if (user.getRole() == Role.USER) {
            return UserSignupResponse.builder()
                    .identifier(user.getIdentifier())
                    .role(user.getRole())
                    .providerInfo(user.getProviderInfo())
                    .userMatchStatus(user.getUserMatchStatus())
                    .build();
        } else {
            throw new BusinessException(MEMBER_NOT_UPDATED);
        }
    }

    /**
     * 프로필 정보를 수정하는 메서드
     */
    @Transactional
    public UserProfileUpdateResponse updateProfile(String identifier, ProviderInfo providerInfo, String key, String introduction) {

        User user = userRepository.findByOAuthInfo(identifier, providerInfo)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        String profileImageUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);

        user.profileUpdate(profileImageUrl, introduction);

        userRepository.save(user);

        System.out.println(user.getProviderInfo());
        System.out.println(user.getIntroduction());
        System.out.println(user.getProfileImageUrl());
        System.out.println(user.getIdentifier());

        return UserProfileUpdateResponse.builder()
                .identifier(user.getIdentifier())
                .providerInfo(user.getProviderInfo())
                .profileImageUrl(user.getProfileImageUrl())
                .introduction(user.getIntroduction())
                .build();
    }

}
