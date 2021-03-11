package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.Technology;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;

import static com.aqualen.vsu.enums.TechnologyName.JAVA;
import static com.aqualen.vsu.enums.TechnologyName.PYTHON;
import static com.aqualen.vsu.enums.TournamentStatus.CREATED;
import static java.time.LocalDate.now;
import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
            user.setRatings(Collections.singletonList(RatingByTechnology.builder().deviation(i).technology(JAVA).mean(i + 25).user(user).build()));
            testEntityManager.persistAndFlush(user);
            testEntityManager.persistAndFlush(RatingByTechnology.builder().technology(PYTHON).user(User.builder().id(1L).build()).build());
        }
    }

    @Test
    void findByTechnologyOrderByRating() {
        PageRequest request = PageRequest.of(0, 2);
        List<RatingByTechnology> result = repository.findByTechnologyAndUserRoleOrderByRating(JAVA, UserRole.USER, request);

        assertThat(result).hasSize(2);
    }

    @Test
    void checkPageable(){
        PageRequest request = PageRequest.of(0, 1);
        List<RatingByTechnology> result = repository.findByTechnologyAndUserRoleOrderByRating(JAVA, UserRole.USER, request);

        assertThat(result).hasSize(1);
    }

    @Test
    void checkRating() {
        RatingByTechnology rating = repository.getOne(1L);
        assertThat(rating.getRating()).isEqualTo(23);

        rating.setDeviation(2.0);
        repository.saveAndFlush(rating);
        assertThat(rating.getRating()).isNotEqualTo(23);
    }
}