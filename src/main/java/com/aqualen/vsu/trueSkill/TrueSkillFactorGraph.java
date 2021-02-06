package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.trueSkill.FactorGraphs.FactorGraphLayer;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.ScheduleSequence;
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

    public void buildGraph(GameInfo gameInfo, List<Player> players) {
        Object lastOutput = null;

        for(FactorGraphLayer currentLayer : layers)
        {
            if (lastOutput != null) {
                currentLayer.setRawInputVariablesGroups(lastOutput);
            }

            currentLayer.buildLayer(gameInfo, players);

            lastOutput = currentLayer.getRawOutputVariablesGroups();
        }
    }

    public void runSchedule() {
        Schedule<GaussianDistribution> fullSchedule = createFullSchedule();
        double fullScheduleDelta = fullSchedule.Visit();
    }

    private Schedule<GaussianDistribution> createFullSchedule()
    {
        List<Schedule<GaussianDistribution>> fullSchedule = new ArrayList<>();

        for (TrueSkillFactorGraphLayer<GaussianDistribution> currentLayer : layers)
        {
            Schedule<GaussianDistribution> currentPriorSchedule = currentLayer.createPriorSchedule();
            if (currentPriorSchedule != null)
            {
                fullSchedule.add(currentPriorSchedule);
            }
        }

        // Casting to IEnumerable to get the LINQ Reverse()
        List<TrueSkillFactorGraphLayer<GaussianDistribution>> allLayers = layers;
        Collections.reverse(allLayers);

        for (TrueSkillFactorGraphLayer<GaussianDistribution> currentLayer : allLayers)
        {
            Schedule<GaussianDistribution> currentPosteriorSchedule = currentLayer.createPosteriorSchedule();
            if (currentPosteriorSchedule != null)
            {
                fullSchedule.add(currentPosteriorSchedule);
            }
        }

        return new ScheduleSequence<>("Full schedule", fullSchedule);
    }
}
