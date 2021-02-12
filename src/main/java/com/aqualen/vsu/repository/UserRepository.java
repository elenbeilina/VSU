package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByRole(UserRole role);
}
