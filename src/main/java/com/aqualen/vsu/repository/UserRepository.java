package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.enums.UserRole;
import com.aqualen.vsu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByRole(UserRole role);
}
