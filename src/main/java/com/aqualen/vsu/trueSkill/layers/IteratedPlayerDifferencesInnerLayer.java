package com.aqualen.vsu.trueSkill.layers;// The whole purpose of this is to do a loop on the bottom

import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
@AllArgsConstructor
public class IteratedPlayerDifferencesInnerLayer extends
        TrueSkillFactorGraphLayer <Variable<GaussianDistribution>,Variable<GaussianDistribution>> {
    private final PlayerDifferencesComparisonLayer teamDifferencesComparisonLayer;
    private final PlayerPerformancesToPlayerPerformanceDifferencesLayer
            teamPerformancesToTeamPerformanceDifferencesLayer;

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        teamPerformancesToTeamPerformanceDifferencesLayer.setRawInputVariablesGroups(getInputVariables());
        teamPerformancesToTeamPerformanceDifferencesLayer.buildLayer(gameInfo, players);

        teamDifferencesComparisonLayer.setRawInputVariablesGroups(
                teamPerformancesToTeamPerformanceDifferencesLayer.getRawOutputVariablesGroups());
        teamDifferencesComparisonLayer.buildLayer(gameInfo, players);
    }
}