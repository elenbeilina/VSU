package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.DrawMargin;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.DefaultVariable;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.factors.GaussianFactor;
import com.aqualen.vsu.trueSkill.factors.GaussianGreaterThanFactor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerDifferencesComparisonLayer extends
        TrueSkillFactorGraphLayer<Variable<GaussianDistribution>, DefaultVariable<GaussianDistribution>> {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        List<List<Variable<GaussianDistribution>>> inputVariables = getInputVariables();

        for (List<Variable<GaussianDistribution>> playerDifference : inputVariables) {
            GaussianFactor factor = new GaussianGreaterThanFactor(playerDifference.get(0));

            addLayerFactor(factor);
        }
    }
}