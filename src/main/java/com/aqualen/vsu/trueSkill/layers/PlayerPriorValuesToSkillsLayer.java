package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.ScheduleStep;
import com.aqualen.vsu.trueSkill.Factors.GaussianPriorFactor;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.Rating;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.Square;

// We intentionally have no Posterior schedule since the only purpose here is to
@Component
public class PlayerPriorValuesToSkillsLayer extends TrueSkillFactorGraphLayer<GaussianDistribution> {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        for (Player currentPlayer : players) {
            KeyedVariable<User, GaussianDistribution> playerSkill =
                    createSkillOutputVariable(currentPlayer.getUser());

            addLayerFactor(createPriorFactor(currentPlayer.getRating(),
                    playerSkill, gameInfo));

            addToOutputVariables(playerSkill);
        }
    }

    private KeyedVariable<User, GaussianDistribution> createSkillOutputVariable(User key) {
        return new KeyedVariable<>(
                key,
                String.format("%s's skill", key),
                new GaussianDistribution());
    }

    private GaussianPriorFactor createPriorFactor(Rating priorRating,
                                                  KeyedVariable<User, GaussianDistribution> skillsVariable,
                                                  GameInfo gameInfo) {
        return new GaussianPriorFactor(priorRating.getMean(),
                Square(priorRating.getStandardDeviation()) +
                        Square(gameInfo.getDynamicsFactor()), skillsVariable);
    }

    @Override
    public Schedule<GaussianDistribution> createPriorSchedule() {
        List<Schedule<GaussianDistribution>> priorToSkillStep = getLocalFactors()
                .stream()
                .map(prior -> new ScheduleStep("Prior to Skill Step", prior, 0))
                .collect(Collectors.toList());
        return scheduleSequence(priorToSkillStep,
                "All priors");
    }
}
