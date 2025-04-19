package com.java.backend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.java.backend.common.dto.ErrorResponseDTO;
import com.java.backend.common.dto.SignupRequestDTO;
import com.java.backend.common.dto.SignupResponseDTO;
import com.java.backend.common.enums.MemberType;
import com.java.backend.domain.User;
import com.java.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class SignupTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    private User user;
    private final String signupUri = "/auth/signup";

    @BeforeAll
    void init() {
        user = new User("test@example.com", "1234", MemberType.USER);
    }

    @Test
    void signupSuccess() {
        SignupRequestDTO requestDTO = new SignupRequestDTO(
                MemberType.USER.name(),
                user.getEmail(),
                "tester",
                user.getPassword()
        );

        HttpEntity<SignupRequestDTO> request = new HttpEntity<>(requestDTO, null);

        ResponseEntity<SignupResponseDTO> response = restTemplate.exchange(
                signupUri,
                HttpMethod.POST,
                request,
                SignupResponseDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        User savedUser = userRepository.find("tester");
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertEquals(savedUser.getPassword(), user.getPassword());
        assertEquals(savedUser.getMemberType(), user.getMemberType());
    }

    @Test
    void alreadySignup() {
        userRepository.save("tester", user);

        SignupRequestDTO requestDTO = new SignupRequestDTO(
                MemberType.USER.name(),
                user.getEmail(),
                "tester",
                user.getPassword()
        );

        HttpEntity<SignupRequestDTO> request = new HttpEntity<>(requestDTO, null);

        ResponseEntity<ErrorResponseDTO> response = restTemplate.exchange(
                signupUri,
                HttpMethod.POST,
                request,
                ErrorResponseDTO.class
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("DUPLICATE_SIGNUP",  response.getBody().code());
        assertEquals("이미 가입된 사용자입니다.", response.getBody().message());
    }

    @AfterEach
    void clear() {
        userRepository.clear();
    }
}
