package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.Technology;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;

import static com.aqualen.vsu.enums.TechnologyName.JAVA;
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
            Tournament tournament = Tournament.builder()
                    .name("tournament" + i)
                    .technologies(Collections.singletonList(Technology.builder().key(Technology.TechnologyKey.builder().technology(JAVA).build()).build()))
                    .status(CREATED)
                    .startDate(now())
                    .endDate(now())
                    .sponsorId(1).build();
            tournament.setTechnologies(Collections.singletonList(Technology.builder().key(Technology.TechnologyKey.builder().technology(JAVA).tournament(tournament).build()).build()));
            testEntityManager.persistAndFlush(tournament);
        }
    }

    @Test
    void findBySponsorId() {
        List<Tournament> tournaments = repository.findBySponsorId(1L);

        assertThat(tournaments).hasSize(2);
    }

    @Test
    void getTechnologies(){
        Tournament tournament = repository.getOne(1L);

        assertThat(tournament.getTechnologies()).hasSize(1);
        Technology technology = tournament.getTechnologies().get(0);
        assertThat(technology.getTechnology()).isEqualTo(JAVA);
    }
}