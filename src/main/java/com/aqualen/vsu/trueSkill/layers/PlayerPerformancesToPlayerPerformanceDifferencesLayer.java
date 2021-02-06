package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.FactorGraphs.FactorGraphLayer;
import com.aqualen.vsu.trueSkill.FactorGraphs.KeyedVariable;
import com.aqualen.vsu.trueSkill.FactorGraphs.Variable;
import com.aqualen.vsu.trueSkill.Factors.GaussianWeightedSumFactor;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerPerformancesToPlayerPerformanceDifferencesLayer extends FactorGraphLayer
        implements TrueSkillFactorGraphLayer {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        List<KeyedVariable<User, GaussianDistribution>> inputVariables = getInputVariables();

        for (int i = 0; i < inputVariables.size() - 1; i++) {
            Variable<GaussianDistribution> stronger = inputVariables.get(i);
            Variable<GaussianDistribution> weaker = inputVariables.get(i + 1);

            Variable<GaussianDistribution> currentDifference = createOutputVariable();
            addLayerFactor(createPlayerPerformanceToDifferenceFactor(stronger, weaker, currentDifference));

            // REVIEW: Does it make sense to have groups of one?
            addToOutputVariables((KeyedVariable<User, GaussianDistribution>) currentDifference);
        }
    }

    private Variable<GaussianDistribution> createOutputVariable() {
        return new Variable<>("Players performance difference", new GaussianDistribution());
    }

    private GaussianWeightedSumFactor createPlayerPerformanceToDifferenceFactor(
            Variable<GaussianDistribution> stronger, Variable<GaussianDistribution> weaker,
            Variable<GaussianDistribution> output) {
        return new GaussianWeightedSumFactor(output, Arrays.asList(stronger, weaker),
                new double[]{1.0, -1.0});
    }
}