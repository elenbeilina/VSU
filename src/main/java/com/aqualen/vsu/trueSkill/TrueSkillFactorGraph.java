package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.trueSkill.FactorGraphs.FactorGraphLayer;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.ScheduleSequence;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.layers.PlayerPriorValuesToSkillsLayer;
import com.aqualen.vsu.trueSkill.layers.TrueSkillFactorGraphLayer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class TrueSkillFactorGraph {
    private final List<TrueSkillFactorGraphLayer<GaussianDistribution>> layers;
    private final PlayerPriorValuesToSkillsLayer priorLayer;

    public void buildGraph(GameInfo gameInfo, List<Player> players) {
        Object lastOutput = null;

        for (FactorGraphLayer<GaussianDistribution> currentLayer : layers) {
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

        for (TrueSkillFactorGraphLayer<GaussianDistribution> currentLayer : layers) {
            Schedule<GaussianDistribution> currentPriorSchedule = currentLayer.createPriorSchedule();
            if (currentPriorSchedule != null) {
                fullSchedule.add(currentPriorSchedule);
            }
        }

        // Casting to IEnumerable to get the LINQ Reverse()
        List<TrueSkillFactorGraphLayer<GaussianDistribution>> allLayers = layers;
        Collections.reverse(allLayers);

        for (TrueSkillFactorGraphLayer<GaussianDistribution> currentLayer : allLayers) {
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
