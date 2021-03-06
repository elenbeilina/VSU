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
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.aqualen.vsu.enums.TournamentLabel.JAVA;
import static com.aqualen.vsu.enums.TournamentStatus.CLOSED;
import static com.aqualen.vsu.enums.TournamentStatus.CREATED;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
                .sponsorId(1).build());

        testEntityManager.flush();
        for (long i = 2; i <= 3; i++) {
            repository.save(Participants.builder()
                    .id(new ParticipantKey(1, i))
                    .tournament(Tournament.builder().id(1L).build())
                    .user(User.builder().id(i).build()).build());
        }
    }

    @Test
    void findByTournamentId() {
        List<Participants> result = repository.findByTournamentId(1);

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
    }

    @Test
    void findByUserIdAndTournamentStatusNot() {
        List<Participants> result = repository.findByUserIdAndTournamentStatusNot(2, CLOSED);
        assertThat(result).isNotEmpty().hasSize(1);

        List<Participants> emptyResult = repository.findByUserIdAndTournamentStatusNot(2, CREATED);
        assertThat(emptyResult).isEmpty();
    }

    @Test
    void updateTask() {
        repository.updateTask(1, 2, "task1");

        String singleResult = testEntityManager.getEntityManager()
                .createNativeQuery("select p.task from vsu.participants p where user_id = 2 and tournament_id = 1")
                .getSingleResult().toString();
        assertThat(singleResult).isEqualTo("task1");
    }

    @Test
    void deleteByTournamentId() {
        List<Participants> participants = repository.findAll();
        assertThat(participants).hasSize(2);

        repository.deleteByTournamentId(participants.get(0).getTournament().getId());

        List<Participants> afterDeletion = repository.findAll();
        assertThat(afterDeletion).hasSize(0);
    }
}