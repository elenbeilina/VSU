package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.trueSkill.FactorGraphs.FactorGraphLayerBase;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.ScheduleSequence;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.layers.IteratedPlayerDifferencesInnerLayer;
import com.aqualen.vsu.trueSkill.layers.PlayerPriorValuesToSkillsLayer;
import com.aqualen.vsu.trueSkill.layers.PlayerSkillsToPerformancesLayer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class TrueSkillFactorGraph {

    private final List<FactorGraphLayerBase<GaussianDistribution>> layers;
    private final PlayerPriorValuesToSkillsLayer priorLayer;

    public TrueSkillFactorGraph(PlayerPriorValuesToSkillsLayer priorLayer, PlayerSkillsToPerformancesLayer performancesLayer,
                                IteratedPlayerDifferencesInnerLayer iteratedPlayerDifferencesLayer) {
        this.layers = Arrays.asList(priorLayer,performancesLayer,iteratedPlayerDifferencesLayer);
        this.priorLayer = priorLayer;
    }

    public void buildGraph(GameInfo gameInfo, List<Player> players) {
        Object lastOutput = null;

        for (FactorGraphLayerBase<GaussianDistribution> currentLayer : layers) {
            if (lastOutput != null) {
                currentLayer.setRawInputVariablesGroups(lastOutput);
            }

            currentLayer.buildLayer(gameInfo, players);

            lastOutput = currentLayer.getRawOutputVariablesGroups();
        }
    }

    public void runSchedule() {
        Schedule<GaussianDistribution> fullSchedule = createFullSchedule();
        double fullScheduleDelta = fullSchedule.visit();
    }

    private Schedule<GaussianDistribution> createFullSchedule() {
        List<Schedule<GaussianDistribution>> fullSchedule = new ArrayList<>();

        for (FactorGraphLayerBase<GaussianDistribution> currentLayer : layers) {
            Schedule<GaussianDistribution> currentPriorSchedule = currentLayer.createPriorSchedule();
            if (currentPriorSchedule != null) {
                fullSchedule.add(currentPriorSchedule);
            }
        }

        // Casting to IEnumerable to get the LINQ Reverse()
        List<FactorGraphLayerBase<GaussianDistribution>> allLayers = layers;
        Collections.reverse(allLayers);

        for (FactorGraphLayerBase<GaussianDistribution> currentLayer : allLayers) {
            Schedule<GaussianDistribution> currentPosteriorSchedule = currentLayer.createPosteriorSchedule();
            if (currentPosteriorSchedule != null) {
                fullSchedule.add(currentPosteriorSchedule);
            }
        }

        return new ScheduleSequence<>("Full schedule", fullSchedule);
    }

    public List<Player> getUpdatedRatings() {
        List<Player> result = new ArrayList<>();

        for (KeyedVariable<Player, GaussianDistribution> currentPlayer : priorLayer.getOutputVariables()) {
            Rating rating = new Rating(currentPlayer.value.mean, currentPlayer.value.standardDeviation);
            Player player = currentPlayer.getKey();
            player.setRating(rating);
            result.add(player);
        }

        return result;
    }
}
