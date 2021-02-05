package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.FactorGraphs.FactorGraphLayer;
import com.aqualen.vsu.trueSkill.FactorGraphs.KeyedVariable;
import com.aqualen.vsu.trueSkill.Factors.GaussianLikelihoodFactor;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.Square;

@Component
public class PlayerSkillsToPerformancesLayer extends FactorGraphLayer implements TrueSkillFactorGraphLayer {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        for (KeyedVariable<User, GaussianDistribution> playerSkillVariable : getInputVariables()) {
            KeyedVariable<User, GaussianDistribution> playerPerformance =
                    createOutputVariable(playerSkillVariable.getKey());

            addLayerFactor(createLikelihood(playerSkillVariable, playerPerformance, gameInfo));

            addToOutputVariables(playerPerformance);
        }
    }


    private GaussianLikelihoodFactor createLikelihood(KeyedVariable<User, GaussianDistribution> playerSkill,
                                                      KeyedVariable<User, GaussianDistribution> playerPerformance,
                                                      GameInfo gameInfo) {
        return new GaussianLikelihoodFactor(Square(gameInfo.getBeta()), playerPerformance, playerSkill);
    }

    private KeyedVariable<User, GaussianDistribution> createOutputVariable(User key) {
        return new KeyedVariable<>(
                key,
                String.format("%s's performance", key),
                new GaussianDistribution());
    }
}