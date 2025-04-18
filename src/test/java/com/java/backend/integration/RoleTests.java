package com.java.backend.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.java.backend.common.dto.UserResponseDTO;
import com.java.backend.common.enums.MemberType;
import com.java.backend.config.JwtUtil;
import com.java.backend.domain.User;
import com.java.backend.repository.UserRepository;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class RoleTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    private String jwt;
    private User user;

    @BeforeAll
    void setup() {
        // 회원 가입
        user = new User("test@example.com", "1234", MemberType.USER);
        userRepository.save("tester", user);

        // 토큰 발급
        jwt = jwtUtil.createToken("tester", "test@example.com", MemberType.USER);
    }

    @Test
    void getProfilesSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                "/role/user",
                HttpMethod.GET,
                request,
                UserResponseDTO.class
        );

        assertEquals(user, userRepository.find("tester"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("tester", response.getBody().nickname());
        assertEquals("test@example.com", response.getBody().email());
    }

    @Test
    void getProfilesWithoutToken() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/role/user", Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("JWT 토큰이 필요합니다.", response.getBody().get("message"));
    }
}
