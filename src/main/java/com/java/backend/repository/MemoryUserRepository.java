package com.java.backend.repository;

import com.java.backend.common.exception.CustomException;
import com.java.backend.domain.User;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemoryUserRepository implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    public void save(String nickname, User user) {
        if(userMap.containsKey(nickname)) {
            throw new CustomException(HttpStatus.CONFLICT, "DUPLICATE_SIGNUP", "이미 가입된 사용자입니다.");
        }
        userMap.put(nickname, user);
    }

    public User find(String nickname) {
        if(!userMap.containsKey(nickname)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "NO_MATCHING_USER", "해당 사용자가 존재하지 않습니다.");
        }
        return userMap.get(nickname);
    }

    public void clear() {
        userMap.clear();
    }
}
