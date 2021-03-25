package com.aqualen.vsu.logic;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.Technology;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.enums.UserRole;
import com.aqualen.vsu.repository.RatingRepository;
import com.aqualen.vsu.repository.TournamentRepository;
import com.aqualen.vsu.repository.UserRepository;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.Rating;
import com.aqualen.vsu.trueSkill.TrueSkillCalculator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.dto.ParticipantResponse.toPlayer;

@Service
@AllArgsConstructor
public class RatingLogic {

    private final RatingRepository ratingRepository;
    private final TrueSkillCalculator trueSkillCalculator;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    public Page<User> getUsersList(TechnologyName name, Pageable page) {
        return ratingRepository.findByKeyTechnologyAndUserRoleOrderByRating(name, UserRole.USER, page)
                .map(RatingByTechnology::getUser);
    }

    public void rateUsers(long tournamentId, List<ParticipantResponse> requests) {
        Tournament tournament = tournamentRepository.getOne(tournamentId);

        requests.stream()
                .peek(participant -> participant.setUser(userRepository
                        .findById(participant.getUser().getId()).orElse(null)))
                .map(ParticipantResponse::getUser)
                .forEach(user -> addDefaultRatingIfNeeded(tournament, user));

        List<Player> players = requests.stream()
                .map(player -> toPlayer(player, tournament))
                .collect(Collectors.toList());

        trueSkillCalculator.calculateNewRatings(new GameInfo(), players);

        List<User> usersWithUpdatedRating = players.stream()
                .map(Player::getUserWithUpdatedRating)
                .collect(Collectors.toList());
        userRepository.saveAll(usersWithUpdatedRating);
    }

    void addDefaultRatingIfNeeded(Tournament tournament, User user) {
        tournament
                .getTechnologies().stream()
                .map(Technology::extractTechnology)
                .filter(technology -> !ratingRepository.existsByKeyTechnologyAndUser(technology, user))
                .map(technology -> getDefaultRating(technology, user))
                .forEach(user::addRating);
    }

    private RatingByTechnology getDefaultRating(TechnologyName technology, User user) {
        Rating rating = new GameInfo().getDefaultRating();

        return RatingByTechnology.builder()
                .key(RatingByTechnology.Key.builder()
                        .userId(user.getId())
                        .technology(technology).build())
                .user(user)
                .deviation(rating.getStandardDeviation())
                .mean(rating.getMean())
                .build();
    }
}
