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

    private String userJwt;
    private String adminJwt;
    private User user;
    private User admin;

    private final String getProfileUri = "/role/user";
    private final String findUserUri = "/role/admin/tester";

    @BeforeAll
    void setup() {
        // 회원 가입
        user = new User("test@example.com", "1234", MemberType.USER);
        userRepository.save("tester", user);

        admin = new User("admin@example.com", "4321", MemberType.ADMIN);
        userRepository.save("admin", admin);

        // 토큰 발급
        userJwt = jwtUtil.createToken("tester", "test@example.com", MemberType.USER);
        adminJwt = jwtUtil.createToken("admin", "admin@example.com", MemberType.ADMIN);
    }

    @Test
    void getProfilesSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", userJwt);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                getProfileUri,
                HttpMethod.GET,
                request,
                UserResponseDTO.class
        );

        //assertEquals(user, userRepository.find("tester"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("tester", response.getBody().nickname());
        assertEquals("test@example.com", response.getBody().email());
    }

    @Test
    void getProfilesWithoutToken() {
        ResponseEntity<Map> response = restTemplate.getForEntity(getProfileUri, Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("JWT 토큰이 필요합니다.", response.getBody().get("message"));
    }

    @Test
    void findUserSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", adminJwt);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                findUserUri,
                HttpMethod.GET,
                request,
                UserResponseDTO.class
        );

        System.out.println("adminJwt: " + adminJwt);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("tester", response.getBody().nickname());
        assertEquals(user.getEmail(), response.getBody().email());
    }

    @Test
    void findUserWithoutAuthority() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", userJwt);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserResponseDTO> response = restTemplate.exchange(
                findUserUri,
                HttpMethod.GET,
                request,
                UserResponseDTO.class
        );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
