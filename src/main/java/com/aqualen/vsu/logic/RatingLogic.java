package com.aqualen.vsu.logic;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.UserRole;
import com.aqualen.vsu.repository.UserRepository;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.TrueSkillCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingLogic {

    private final UserRepository userRepository;
    private final TrueSkillCalculator trueSkillCalculator;

    public List<User> getUsersList(int count) {
        List<User> users = userRepository.findAllByRole(UserRole.USER);
        sortByRating(users);
        return users.size() > count ? users.subList(0, count) : users;
    }

    public List<User> getUsersList() {
        List<User> users = userRepository.findAllByRole(UserRole.USER);
        sortByRating(users);
        return users;
    }

    private void sortByRating(List<User> users) {
        users.sort(Comparator.comparingDouble(User::getRating)
                .reversed());
    }

    public void rateUsers(List<ParticipantResponse> requests) {
        List<Player> players = requests.stream()
                .map(ParticipantResponse::toPlayer)
                .collect(Collectors.toList());

        trueSkillCalculator.calculateNewRatings(new GameInfo(), players);

        userRepository.saveAll(players.stream()
                .map(Player::getUserWithUpdatedRating)
                .collect(Collectors.toList()));
    }
}
