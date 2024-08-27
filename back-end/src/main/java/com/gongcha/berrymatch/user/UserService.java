package com.gongcha.berrymatch.user;

import com.gongcha.berrymatch.exception.BusinessException;
import com.gongcha.berrymatch.springSecurity.responseDTO.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gongcha.berrymatch.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    public AuthResponse getUserAuthInfo(String identifier) {
        User user = userRepository.findByUsername(identifier)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
        return new AuthResponse(user.getRole());
    }
}
