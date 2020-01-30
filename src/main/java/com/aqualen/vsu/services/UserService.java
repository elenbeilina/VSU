package com.aqualen.vsu.services;

import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    void delete(long id);
    User getById(long id);
    User findByUsername(String username);
    User updateUser(User user);
    List<User> getAll();
    List<User> getUsersByRole(UserRole role);
    List<User> getUsersByRole(UserRole role, int count);
}
