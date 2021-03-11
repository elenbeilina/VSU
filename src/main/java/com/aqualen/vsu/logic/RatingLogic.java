package com.aqualen.vsu.logic;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.TechnologyName;
import com.aqualen.vsu.enums.UserRole;
import com.aqualen.vsu.repository.RatingRepository;
import com.aqualen.vsu.repository.TournamentRepository;
import com.aqualen.vsu.repository.UserRepository;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.TrueSkillCalculator;
import lombok.AllArgsConstructor;
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

    public List<User> getUsersList(TechnologyName name, Pageable page) {
        return ratingRepository.findByTechnologyAndUserRoleOrderByRating(name, UserRole.USER, page)
                .stream().map(RatingByTechnology::getUser)
                .collect(Collectors.toList());
    }

    public void rateUsers(long tournamentId, List<ParticipantResponse> requests) {
        List<Player> players = requests.stream()
                .map(player -> toPlayer(player, tournamentRepository.getOne(tournamentId)))
                .collect(Collectors.toList());

        trueSkillCalculator.calculateNewRatings(new GameInfo(), players);

        List<User> usersWithUpdatedRating = players.stream()
                .map(Player::getUserWithUpdatedRating)
                .collect(Collectors.toList());
        userRepository.saveAll(usersWithUpdatedRating);
    }
}
