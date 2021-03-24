package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;

import static com.aqualen.vsu.enums.TechnologyName.JAVA;
import static com.aqualen.vsu.enums.TechnologyName.PYTHON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RatingRepositoryTest {

    @Autowired
    private RatingRepository repository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        for (double i = 1; i <= 2; i++) {
            User user = User.builder().username("user" + i).firstName("user" + i).studentBookId(String.valueOf(i)).role(UserRole.USER).build();
            user.setRatings(Collections.singletonList(RatingByTechnology.builder().deviation(i).key(RatingByTechnology.Key.builder().technology(JAVA).userId(user.getId()).build()).user(user).mean(i + 25).build()));
            testEntityManager.persistAndFlush(user);
        }
        User user = (User) testEntityManager.getEntityManager()
                .createQuery("select u from User u where u.id = 1")
                .getSingleResult();
        testEntityManager.persistAndFlush(RatingByTechnology.builder().key(RatingByTechnology.Key.builder().technology(PYTHON).userId(1L).build()).user(user).build());
    }

    @Test
    void findByTechnologyOrderByRating() {
        PageRequest request = PageRequest.of(0, 2);
        Page<RatingByTechnology> result = repository.findByKeyTechnologyAndUserRoleOrderByRating(JAVA, UserRole.USER, request);

        assertThat(result).hasSize(2);
    }

    @Test
    void checkPageable() {
        PageRequest request = PageRequest.of(0, 1);
        Page<RatingByTechnology> result = repository.findByKeyTechnologyAndUserRoleOrderByRating(JAVA, UserRole.USER, request);

        assertThat(result).hasSize(1);
    }

    @Test
    void checkRating() {
        RatingByTechnology rating = repository.getOne(RatingByTechnology.Key.builder().technology(JAVA).userId(1L).build());
        assertThat(rating.getRating()).isEqualTo(23);

        rating.setDeviation(2.0);
        repository.saveAndFlush(rating);
        assertThat(rating.getRating()).isNotEqualTo(23);
    }

    @Test
    void existsByTechnologyAndUserId() {
        User user = User.builder().id(2L).build();
        assertTrue(repository.existsByKeyTechnologyAndUser(JAVA, user));

        assertFalse(repository.existsByKeyTechnologyAndUser(PYTHON, user));
    }
}