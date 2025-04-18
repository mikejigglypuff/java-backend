package com.java.backend.service;

import com.java.backend.common.dto.UserResponseDTO;
import com.java.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserResponseDTO getProfile(String nickname) {
        return new UserResponseDTO(nickname, userRepository.find(nickname).getEmail());
    }
}
