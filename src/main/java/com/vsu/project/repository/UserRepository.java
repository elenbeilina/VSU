package com.vsu.project.repository;

import com.vsu.project.entity.User;
import com.vsu.project.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByRole(UserRole role);
}
