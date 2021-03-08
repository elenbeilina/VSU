package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.aqualen.vsu.enums.TournamentLabel.JAVA;
import static com.aqualen.vsu.enums.TournamentStatus.CREATED;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository repository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        testEntityManager.persistAndFlush(User.builder().username("sponsor").firstName("sponsor").studentBookId("2").role(UserRole.SPONSOR).build());
        for (int i = 1; i <= 2; i++) {
            testEntityManager.persistAndFlush(Tournament.builder()
                    .name("tournament" + i)
                    .label(JAVA)
                    .status(CREATED)
                    .startDate(now())
                    .endDate(now())
                    .sponsorId(1).build());
        }
    }

    @Test
    void findBySponsorId() {
        List<Tournament> tournaments = repository.findBySponsorId(1L);

        assertThat(tournaments).hasSize(2);
    }
}