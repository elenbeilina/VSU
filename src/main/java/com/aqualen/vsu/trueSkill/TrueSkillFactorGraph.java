package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.trueSkill.factorGraphs.FactorGraphLayerBase;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleSequence;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.layers.PlayerTechnologyKnowledgePriorValuesToSkillsLayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrueSkillFactorGraph {

    private final List<FactorGraphLayerBase> layers;
    private final PlayerTechnologyKnowledgePriorValuesToSkillsLayer priorLayer;

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

    public List<Player> getUpdatedRatings(List<Player> players) {
        List<List<KeyedVariable<Technology, GaussianDistribution>>> outputVariables = priorLayer.getOutputVariables();
        for (int i = 0; i < outputVariables.size(); i++) {
            List<Technology> skills = new ArrayList<>();
            for (KeyedVariable<Technology, GaussianDistribution> technologyKnowledge : outputVariables.get(i)) {
                final Rating rating = new Rating(technologyKnowledge.getValue().getMean(),
                        technologyKnowledge.getValue().getStandardDeviation());
                Technology technology = technologyKnowledge.getKey();
                technology.setRating(rating);

                skills.add(technology);
            }
            players.get(i).setSkills(skills);
        }
        return players;
    }
}
