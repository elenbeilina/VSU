package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.trueSkill.factorGraphs.FactorGraphLayerBase;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleSequence;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.layers.IteratedPlayerDifferencesInnerLayer;
import com.aqualen.vsu.trueSkill.layers.PlayerPriorValuesToSkillsLayer;
import com.aqualen.vsu.trueSkill.layers.PlayerSkillsToPerformancesLayer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TrueSkillFactorGraph {

    private final List<FactorGraphLayerBase> layers;
    private final PlayerPriorValuesToSkillsLayer priorLayer;

    public TrueSkillFactorGraph(PlayerPriorValuesToSkillsLayer priorLayer, PlayerSkillsToPerformancesLayer performancesLayer,
                                IteratedPlayerDifferencesInnerLayer iteratedPlayerDifferencesLayer) {
        this.layers = List.of(priorLayer, performancesLayer, iteratedPlayerDifferencesLayer);
        this.priorLayer = priorLayer;
    }

    public void buildGraph(GameInfo gameInfo, List<Player> players) {
        Object lastOutput = null;

        for (var currentLayer : layers) {
            if (lastOutput != null) {
                currentLayer.setRawInputVariablesGroups(lastOutput);
            }

            currentLayer.buildLayer(gameInfo, players);

            lastOutput = currentLayer.getRawOutputVariablesGroups();
        }
    }

    public void runSchedule() {
        Schedule fullSchedule = createFullSchedule();
        double fullScheduleDelta = fullSchedule.visit();
        System.out.println(fullScheduleDelta);
    }

    private Schedule createFullSchedule() {
        List<Schedule> fullSchedule = new ArrayList<>();

        for (var currentLayer : layers) {
            Schedule currentPriorSchedule = currentLayer.createPriorSchedule();
            if (currentPriorSchedule != null) {
                fullSchedule.add(currentPriorSchedule);
            }
        }

        List<FactorGraphLayerBase> allLayers = new ArrayList<>(layers);
        Collections.reverse(allLayers);

        for (var currentLayer : allLayers) {
            Schedule currentPosteriorSchedule = currentLayer.createPosteriorSchedule();
            if (currentPosteriorSchedule != null) {
                fullSchedule.add(currentPosteriorSchedule);
            }
        }

        return new ScheduleSequence("Full schedule", fullSchedule);
    }

    public List<Player> getUpdatedRatings() {
        List<Player> result = new ArrayList<>();

        for (KeyedVariable<Player, GaussianDistribution> currentPlayer : priorLayer.getOutputVariables()) {
            Rating rating = new Rating(currentPlayer.getValue().getMean(), currentPlayer.getValue().getStandardDeviation());
            Player player = currentPlayer.getKey();
            player.setRating(rating);
            result.add(player);
        }

        return result;
    }
}
