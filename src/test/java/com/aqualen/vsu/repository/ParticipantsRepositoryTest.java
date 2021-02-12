package com.aqualen.vsu.repository;

import com.aqualen.vsu.entity.ParticipantKey;
import com.aqualen.vsu.entity.Participants;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static com.aqualen.vsu.enums.TournamentLabel.JAVA;
import static com.aqualen.vsu.enums.TournamentStatus.CREATED;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ParticipantsRepositoryTest {
    @Autowired
    private ParticipantsRepository repository;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        for (int i = 1; i <= 3; i++) {
            testEntityManager.persistAndFlush(User.builder().username("user" + i).firstName("user" + i).studentBookId(String.valueOf(i)).role(UserRole.USER).build());
        }
        testEntityManager.persistAndFlush(Tournament.builder()
                .label(JAVA)
                .status(CREATED)
                .startDate(now())
                .endDate(now())
                .sponsor(User.builder().id(1).build()).build());

        testEntityManager.flush();
        for (long i = 2; i <= 3; i++) {
            repository.save(Participants.builder()
                    .id(new ParticipantKey(1, i))
                    .tournament(Tournament.builder().id(1).build())
                    .user(User.builder().id(i).build()).build());
        }
    }

    @Test
    void findByTournamentId() {
        List<Participants> result = repository.findByTournamentId(1);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }
}