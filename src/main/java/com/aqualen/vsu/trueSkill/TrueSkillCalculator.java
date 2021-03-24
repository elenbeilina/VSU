package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.utils.ArgumentUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class TrueSkillCalculator {

    private final TrueSkillFactorGraph trueSkillFactorGraph;

    public List<Player> calculateNewRatings(GameInfo gameInfo, List<Player> players) {

        ArgumentUtils.argumentNotNull(gameInfo, "gameInfo");
        Collections.sort(players);

        trueSkillFactorGraph.buildGraph(gameInfo, players);
        trueSkillFactorGraph.runSchedule();

        return trueSkillFactorGraph.getUpdatedRatings(players);
    }
}
