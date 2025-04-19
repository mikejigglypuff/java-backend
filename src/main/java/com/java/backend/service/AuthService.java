package com.java.backend.service;

import com.java.backend.common.exception.CustomException;
import com.java.backend.config.JwtUtil;
import com.java.backend.domain.User;
import com.java.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public String login(String nickname, String password) {
        User loginUser = userRepository.find(nickname);
        if(!password.equals(loginUser.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "LOGIN_FAILURE", "비밀번호가 잘못되었습니다.");
        }
        return jwtUtil.createToken(nickname, loginUser.getEmail(), loginUser.getMemberType());
    }

    public String signup(String nickname, User user) {
        userRepository.save(nickname, user);
        return "회원가입 성공";
    }
}
