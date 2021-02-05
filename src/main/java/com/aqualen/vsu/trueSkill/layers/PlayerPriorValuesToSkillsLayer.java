package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.FactorGraphs.FactorGraphLayer;
import com.aqualen.vsu.trueSkill.FactorGraphs.KeyedVariable;
import com.aqualen.vsu.trueSkill.Factors.GaussianPriorFactor;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.Rating;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.Square;

// We intentionally have no Posterior schedule since the only purpose here is to
@Component
public class PlayerPriorValuesToSkillsLayer extends FactorGraphLayer implements TrueSkillFactorGraphLayer {

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
}
