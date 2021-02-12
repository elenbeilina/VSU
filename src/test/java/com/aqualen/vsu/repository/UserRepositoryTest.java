package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        List<User> users = new ArrayList<>();

        for (long i = 1; i <= 3; i++) {
            users.add(User.builder().username("user" + i).firstName("user" + i).studentBookId(String.valueOf(i)).role(UserRole.USER).build());
        }

        users.forEach(user -> testEntityManager.persistAndFlush(user));
    }


    @Test
    void findByUsername() {
        List<User> all = userRepository.findAll();

        assertThat(all).isNotNull();
    }
}