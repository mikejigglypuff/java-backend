package com.java.backend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.java.backend.common.dto.ErrorResponseDTO;
import com.java.backend.common.dto.LoginRequestDTO;
import com.java.backend.common.dto.LoginResponseDTO;
import com.java.backend.common.enums.MemberType;
import com.java.backend.config.JwtUtil;
import com.java.backend.domain.User;
import com.java.backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
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
public class LoginTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    private User user;

    private final String loginUri = "/auth/login";

    @BeforeAll
    void setup() {
        // 회원 가입
        user = new User("test@example.com", "1234", MemberType.USER);
        userRepository.save("tester", user);
    }

    @Test
    void loginSuccess() {
        LoginRequestDTO requestDTO = new LoginRequestDTO(
                "tester", user.getPassword()
        );

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(requestDTO, null);

        ResponseEntity<LoginResponseDTO> response = restTemplate.exchange(
                loginUri,
                HttpMethod.POST,
                request,
                LoginResponseDTO.class
        );

        String responseToken = jwtUtil.substringToken(response.getBody().token());
        Claims claims = jwtUtil.extractClaims(responseToken);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("tester", claims.get("nickname"));
        assertEquals("test@example.com", claims.get("email"));
        assertEquals(MemberType.USER.name(), (String) claims.get("userRole"));
    }

    @Test
    void loginWithWrongPW() {
        LoginRequestDTO wrongRequestDTO = new LoginRequestDTO(
                "tester", "wrongPassword"
        );

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(wrongRequestDTO, null);

        ResponseEntity<ErrorResponseDTO> response = restTemplate.exchange(
                loginUri,
                HttpMethod.POST,
                request,
                ErrorResponseDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("LOGIN_FAILURE", response.getBody().code());
        assertEquals("비밀번호가 잘못되었습니다.", response.getBody().message());
    }
}
