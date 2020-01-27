package com.vsu.project.services;

import com.vsu.project.entity.User;
import com.vsu.project.entity.enums.UserRole;

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
