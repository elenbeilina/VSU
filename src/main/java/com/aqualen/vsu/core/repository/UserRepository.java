package com.aqualen.vsu.core.repository;

import com.aqualen.vsu.core.entity.enums.UserRole;
import com.aqualen.vsu.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByRole(UserRole role);
}
