package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TrueSkillCalculator {

    private final TrueSkillFactorGraph trueSkillFactorGraph;

    //TODO:not finished
    public Map<User, Rating> calculateNewRatings(GameInfo gameInfo, List<Player> players) {

        Guard.argumentNotNull(gameInfo, "gameInfo");
        Collections.sort(players);

        trueSkillFactorGraph.buildGraph(gameInfo, players);


        return null;
    }
}
