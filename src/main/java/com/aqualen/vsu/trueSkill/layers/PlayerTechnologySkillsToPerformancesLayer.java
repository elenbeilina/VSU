package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.Technology;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleStep;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.factors.GaussianLikelihoodFactor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.GAUSSIAN_LINE;
import static com.aqualen.vsu.trueSkill.GaussianDistribution.square;

@Component
@Order(2)
@RequestScope
public class PlayerTechnologySkillsToPerformancesLayer extends
        TrueSkillFactorGraphLayer<KeyedVariable<Technology, GaussianDistribution>, KeyedVariable<Technology, GaussianDistribution>> {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        for (List<KeyedVariable<Technology, GaussianDistribution>> technologySkills : getInputVariables()) {
            List<KeyedVariable<Technology, GaussianDistribution>> technologyPlayerPerformances = new ArrayList<>();

            for (KeyedVariable<Technology, GaussianDistribution> technologySkill : technologySkills) {

                KeyedVariable<Technology, GaussianDistribution> playerTechnologyPerformance =
                        createOutputVariable(technologySkill.getKey());

                addLayerFactor(createLikelihood(technologySkill, playerTechnologyPerformance, gameInfo));

                technologyPlayerPerformances.add(playerTechnologyPerformance);
            }

            addToOutputVariables(technologyPlayerPerformances);
        }
    }

    private GaussianLikelihoodFactor createLikelihood(KeyedVariable<Technology, GaussianDistribution> playerTechnologySkill,
                                                      KeyedVariable<Technology, GaussianDistribution> playerTechnologyPerformance,
                                                      GameInfo gameInfo) {
        return new GaussianLikelihoodFactor(square(gameInfo.getBeta()), playerTechnologyPerformance, playerTechnologySkill);
    }

    private KeyedVariable<Technology, GaussianDistribution> createOutputVariable(Technology key) {
        return new KeyedVariable<>(
                key,
                String.format("%s's performance", key),
                GAUSSIAN_LINE);
    }

    @Override
    public Schedule createPriorSchedule() {
        List<Schedule> skillToPerfStep = getLocalFactors()
                .stream()
                .map(likelihood -> new ScheduleStep<>("Skill to Perf step", likelihood, 0))
                .collect(Collectors.toList());
        return scheduleSequence(skillToPerfStep,
                "All skill to performance sending");
    }

    @Override
    public Schedule createPosteriorSchedule() {
        List<Schedule> names = getLocalFactors()
                .stream()
                .map(likelihood -> new ScheduleStep<>("name", likelihood, 1))
                .collect(Collectors.toList());
        return scheduleSequence(names,
                "All skill to performance sending");
    }
}