package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.DrawMargin;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.DefaultVariable;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.Factors.GaussianFactor;
import com.aqualen.vsu.trueSkill.Factors.GaussianGreaterThanFactor;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerDifferencesComparisonLayer extends
        TrueSkillFactorGraphLayer<Variable<GaussianDistribution>, DefaultVariable<GaussianDistribution>> {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        List<Variable<GaussianDistribution>> inputVariables = getInputVariables();

        double epsilon = DrawMargin.GetDrawMarginFromDrawProbability(gameInfo.getDrawProbability(), gameInfo.getBeta());
        for (Variable<GaussianDistribution> teamDifference : inputVariables) {
            GaussianFactor factor = new GaussianGreaterThanFactor(teamDifference, epsilon);

            addLayerFactor(factor);
        }
    }
}