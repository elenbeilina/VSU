package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TrueSkillCalculator {

    private final TrueSkillFactorGraph trueSkillFactorGraph;

    //TODO:not finished
    public Map<User,Rating> calculateNewRatings(GameInfo gameInfo,List<Player> players){
        if(Objects.nonNull(gameInfo)) {
            trueSkillFactorGraph.buildGraph(gameInfo, players);
        }
        return null;
    }
}
