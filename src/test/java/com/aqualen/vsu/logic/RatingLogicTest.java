package com.aqualen.vsu.logic;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.Technology;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.repository.RatingRepository;
import com.aqualen.vsu.repository.TournamentRepository;
import com.aqualen.vsu.repository.UserRepository;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.Rating;
import com.aqualen.vsu.trueSkill.TrueSkillCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aqualen.vsu.dto.ParticipantResponse.toPlayer;
import static com.aqualen.vsu.enums.TechnologyName.*;
import static com.aqualen.vsu.trueSkill.Player.getUserWithUpdatedRating;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class RatingLogicTest {

    @Mock
    private TournamentRepository tournamentRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private TrueSkillCalculator trueSkillCalculator;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RatingLogic logic;

    @Captor
    ArgumentCaptor<List<User>> users;
    @Captor
    ArgumentCaptor<List<Player>> players;

    private ParticipantResponse rateRequest;
    private Tournament tournament;

    @BeforeEach
    void setUp() {
        User user = User.builder().id(1).build();
        List<RatingByTechnology> rating = new ArrayList<>() {{
            add(RatingByTechnology.builder().user(user).key(RatingByTechnology.Key.builder().technology(JAVA).build()).deviation(21.0).mean(2.0).build());
            add(RatingByTechnology.builder().user(user).key(RatingByTechnology.Key.builder().technology(PYTHON).build()).deviation(11.0).mean(3.0).build());
        }};
        user.setRatings(rating);
        rateRequest = new ParticipantResponse(user, 1, "1");

        tournament = Tournament.builder().technologies(new ArrayList<>() {{
            add(Technology.builder()
                    .key(Technology.Key.builder().technology(JAVA).build()).percent(100).build());
        }}).build();
    }

    @Test
    void checkToPlayer() {
        Player player = toPlayer(rateRequest, tournament);

        assertThat(player).isNotNull();
        assertThat(player.getRank()).isEqualTo(rateRequest.getGrade());
        assertThat(player.getSkills()).hasSize(1);
        assert player.getSkills().get(0).getLanguage() == JAVA;
    }

    @Test
    void checkGetUsersWithUpdatedRatings() {
        Player player = Player.builder()
                .user(rateRequest.getUser())
                .rank(1)
                .skills(Collections.singletonList(com.aqualen.vsu.trueSkill.Technology.builder()
                        .language(JAVA)
                        .rating(new Rating(50, 10)).build()))
                .build();

        User result = getUserWithUpdatedRating(player);

        assertThat(result.getRatings()).hasSize(2);
        RatingByTechnology rating = result.getRatings().get(0);
        assert rating.extractTechnology() == JAVA;
        assert rating.getMean() == 50;
        assert rating.getDeviation() == 10;

    }

    @Test
    void rateUsers() {
        when(tournamentRepository.getOne(anyLong())).thenReturn(tournament);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(rateRequest.getUser()));
        when(ratingRepository.existsByKeyTechnologyAndUser(any(), any()))
                .thenReturn(true);
        logic.rateUsers(1L, Collections.singletonList(rateRequest));

        verify(trueSkillCalculator).calculateNewRatings(any(), players.capture());
        List<Player> playerList = players.getValue();
        assertThat(playerList).hasSize(1);

        verify(userRepository).saveAll(users.capture());
        List<User> userList = users.getValue();
        assertThat(userList.get(0).getRatings()).hasSize(2);
    }

    @Test
    void addDefaultRating() {
        tournament.getTechnologies().add(Technology.builder().key(Technology.Key.builder().technology(JS).build()).build());
        when(ratingRepository.existsByKeyTechnologyAndUser(any(), any()))
                .thenReturn(true)
                .thenReturn(false);

        assertThat(rateRequest.getUser().getRatings()).hasSize(2);
        logic.addDefaultRatingIfNeeded(tournament, rateRequest.getUser());

        List<RatingByTechnology> result = rateRequest.getUser().getRatings();

        assertThat(result).hasSize(3);
        assert result.get(2).getMean() == 25;
        assert result.get(2).extractTechnology() == JS;
    }

    @Test
    void getUsersList() {
        List<RatingByTechnology> userRatings = rateRequest.getUser().getRatings();
        List<RatingByTechnology> rating = Collections.singletonList(userRatings.get(0));
        when(ratingRepository.findByKeyTechnologyAndUserRoleOrderByRating(any(), any(), any()))
                .thenReturn(new PageImpl<>(rating));

        List<TechnologyName> technologyNames = getTechnologies(userRatings);
        assertThat(technologyNames).hasSize(2);
        assertThat(technologyNames.contains(PYTHON)).isTrue();

        Page<User> usersList = logic.getUsersList(JAVA, Pageable.unpaged());

        List<TechnologyName> resultTechnologies = getTechnologies(usersList.get().findFirst().get().getRatings());
        assertThat(resultTechnologies).hasSize(1);
        assertThat(resultTechnologies.contains(PYTHON)).isFalse();

    }

    private List<TechnologyName> getTechnologies(List<RatingByTechnology> ratings) {
        return ratings.stream()
                .map(RatingByTechnology::getKey)
                .map(RatingByTechnology.Key::getTechnology)
                .collect(Collectors.toList());
    }
}