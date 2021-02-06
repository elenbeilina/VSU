package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.DrawMargin;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.KeyedVariable;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.Factors.GaussianFactor;
import com.aqualen.vsu.trueSkill.Factors.GaussianGreaterThanFactor;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;

import java.util.List;

public class PlayerDifferencesComparisonLayer extends TrueSkillFactorGraphLayer<GaussianDistribution> {

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        List<KeyedVariable<User, GaussianDistribution>> inputVariables = getInputVariables();

        double epsilon = DrawMargin.GetDrawMarginFromDrawProbability(gameInfo.getDrawProbability(), gameInfo.getBeta());
        for (Variable<GaussianDistribution> teamDifference : inputVariables) {
            GaussianFactor factor = new GaussianGreaterThanFactor(teamDifference, epsilon);

            addLayerFactor(factor);
        }
    }
}