package com.java.backend.repository;

import com.java.backend.domain.User;

public interface UserRepository {
    public void save(String nickname, User user);
    public User find(String nickname);
}
