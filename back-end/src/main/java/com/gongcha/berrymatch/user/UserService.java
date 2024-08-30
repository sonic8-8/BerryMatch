package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.exception.ErrorCode;
import com.gongcha.berrymatch.springSecurity.responseDTO.AuthResponse;
import com.gongcha.berrymatch.user.RequestDTO.UserSignupServiceRequest;
import com.gongcha.berrymatch.user.ResponseDTO.UserSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gongcha.berrymatch.exception.ErrorCode.MEMBER_NOT_FOUND;
import static com.gongcha.berrymatch.exception.ErrorCode.MEMBER_NOT_UPDATED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    public User findUserByIdentifier(String identifier) {
        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    public AuthResponse getUserAuthInfo(String identifier) {
        User user = userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
        return new AuthResponse(user.getRole());
    }

    /**
     * FE로 받은 사용자 입력으로 User 정보를 업데이트 해 회원가입 시켜주는 메서드
     */
    @Transactional
    public UserSignupResponse signup(UserSignupServiceRequest request) {

        System.out.println("업데이트 하려고 들어옴");

        User user = userRepository.findByIdentifier(request.getIdentifier())
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));

        System.out.println("찾아오긴 함");
        System.out.println(user.getRole());

        user.update(request);

        System.out.println("업데이트 됨");
        System.out.println(user.getRole());
        System.out.println(user.getIdentifier());

        User savedUser = userRepository.save(user);

        System.out.println(savedUser.getRole());

        if (user.getRole() == Role.USER) {
            return UserSignupResponse.builder()
                    .identifier(user.getIdentifier())
                    .role(user.getRole())
                    .build();
        } else {
            throw new BusinessException(MEMBER_NOT_UPDATED);
        }
    }
}
