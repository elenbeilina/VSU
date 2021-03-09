package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.*;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleStep;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.DefaultVariable;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.factors.GaussianPriorFactor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.GAUSSIAN_LINE;
import static com.aqualen.vsu.trueSkill.GaussianDistribution.square;

@Component
@Order(1)
public class PlayerTechnologyKnowledgePriorValuesToSkillsLayer extends
        TrueSkillFactorGraphLayer<DefaultVariable<GaussianDistribution>, KeyedVariable<Technology, GaussianDistribution>> {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        for (Player player : players) {
            List<KeyedVariable<Technology, GaussianDistribution>> playerSkills = new ArrayList<>();
            for (Technology scope : player.getSkills()) {
                KeyedVariable<Technology, GaussianDistribution> playerSkill =
                        createSkillOutputVariable(scope);

                addLayerFactor(createPriorFactor(scope.getRating(),
                        playerSkill, gameInfo));

                playerSkills.add(playerSkill);
            }
            addToOutputVariables(playerSkills);
        }
    }

    private KeyedVariable<Technology, GaussianDistribution> createSkillOutputVariable(Technology key) {
        return new KeyedVariable<>(
                key,
                String.format("%s's skill", key),
                GAUSSIAN_LINE);
    }

    private GaussianPriorFactor createPriorFactor(Rating priorRating,
                                                  KeyedVariable<Technology, GaussianDistribution> skillsVariable,
                                                  GameInfo gameInfo) {
        return new GaussianPriorFactor(priorRating.getMean(),
                square(priorRating.getStandardDeviation()) +
                        square(gameInfo.getDynamicsFactor()), skillsVariable);
    }

    @Override
    public Schedule createPriorSchedule() {
        List<Schedule> priorToSkillStep = getLocalFactors()
                .stream()
                .map(prior -> new ScheduleStep<>("Prior to Skill Step", prior, 0))
                .collect(Collectors.toList());
        return scheduleSequence(priorToSkillStep,
                "All priors");
    }
}
