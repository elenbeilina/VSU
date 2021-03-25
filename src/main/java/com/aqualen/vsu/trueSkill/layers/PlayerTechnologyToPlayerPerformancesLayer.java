package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.Technology;
import com.aqualen.vsu.trueSkill.factorGraphs.Factor;
import com.aqualen.vsu.trueSkill.factorGraphs.FactorGraphLayer;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleStep;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.factors.GaussianWeightedSumFactor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.GAUSSIAN_LINE;

@Component
@Order(3)
@RequestScope
public class PlayerTechnologyToPlayerPerformancesLayer extends
        FactorGraphLayer<KeyedVariable<Technology, GaussianDistribution>, Variable<GaussianDistribution>> {

    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        for (List<KeyedVariable<Technology, GaussianDistribution>> currentPlayer : getInputVariables()) {
            Variable<GaussianDistribution> playerPerformance = createOutputVariable(currentPlayer);
            addLayerFactor(createTechnologyToPlayerSumFactor(currentPlayer, playerPerformance));

            addToOutputVariables(List.of(playerPerformance));
        }
    }

    public Schedule createPriorSchedule() {
        List<Schedule> schedules = getLocalFactors()
                .stream()
                .map(weightedSumFactor -> new ScheduleStep<>("Perf to Player Perf Step", weightedSumFactor, 0))
                .collect(Collectors.toList());
        return scheduleSequence(schedules, "all technologies perf to player perf schedule");
    }

    protected GaussianWeightedSumFactor createTechnologyToPlayerSumFactor(List<KeyedVariable<Technology, GaussianDistribution>> technologies,
                                                                          Variable<GaussianDistribution> sumVariable) {
        double[] weights = new double[technologies.size()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = (technologies.get(i).getKey()).getPercent();
        }
        return new GaussianWeightedSumFactor(sumVariable, technologies, weights);
    }

    public Schedule createPosteriorSchedule() {

        List<Schedule> schedules = new ArrayList<>();
        for (Factor<GaussianDistribution> currentFactor : getLocalFactors()) {
            // TODO is there an off by 1 error here?
            for (int i = 0; i < currentFactor.getMessages().size(); i++) {
                schedules.add(new ScheduleStep<>(
                        "team sum perf @" + i,
                        currentFactor,
                        i));
            }
        }
        return scheduleSequence(schedules, "all of the player's sum iterations");
    }

    private Variable<GaussianDistribution> createOutputVariable(List<KeyedVariable<Technology, GaussianDistribution>> playerSkills) {
        StringBuilder sb = new StringBuilder();
        for (KeyedVariable<Technology, GaussianDistribution> technology : playerSkills) {
            sb.append(technology.getKey().toString());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());

        return new Variable<>(GAUSSIAN_LINE, "Team[%s]'s performance", sb.toString());
    }
}