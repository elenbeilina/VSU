package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.ScheduleStep;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.Factors.GaussianLikelihoodFactor;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.square;

@Component
@Order(2)
public class PlayerSkillsToPerformancesLayer extends
        TrueSkillFactorGraphLayer<KeyedVariable<Player, GaussianDistribution>, KeyedVariable<Player, GaussianDistribution>> {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        for (KeyedVariable<Player, GaussianDistribution> playerSkillVariable : getInputVariables()) {
            KeyedVariable<Player, GaussianDistribution> playerPerformance =
                    createOutputVariable(playerSkillVariable.getKey());

            addLayerFactor(createLikelihood(playerSkillVariable, playerPerformance, gameInfo));

            addToOutputVariables(playerPerformance);
        }
    }

    private GaussianLikelihoodFactor createLikelihood(KeyedVariable<Player, GaussianDistribution> playerSkill,
                                                      KeyedVariable<Player, GaussianDistribution> playerPerformance,
                                                      GameInfo gameInfo) {
        return new GaussianLikelihoodFactor(square(gameInfo.getBeta()), playerPerformance, playerSkill);
    }

    private KeyedVariable<Player, GaussianDistribution> createOutputVariable(Player key) {
        return new KeyedVariable<>(
                key,
                String.format("%s's performance", key),
                new GaussianDistribution());
    }

    @Override
    public Schedule<GaussianDistribution> createPriorSchedule() {
        List<Schedule<GaussianDistribution>> skillToPerfStep = getLocalFactors()
                .stream()
                .map(likelihood -> new ScheduleStep<>("Skill to Perf step", likelihood, 0))
                .collect(Collectors.toList());
        return scheduleSequence(skillToPerfStep,
                "All skill to performance sending");
    }

    @Override
    public Schedule<GaussianDistribution> createPosteriorSchedule() {
        List<Schedule<GaussianDistribution>> names = getLocalFactors()
                .stream()
                .map(likelihood -> new ScheduleStep<>("name", likelihood, 1))
                .collect(Collectors.toList());
        return scheduleSequence(names,
                "All skill to performance sending");
    }
}