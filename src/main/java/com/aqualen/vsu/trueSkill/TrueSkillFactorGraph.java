package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.trueSkill.layers.TrueSkillFactorGraphLayer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TrueSkillFactorGraph {
    private final List<TrueSkillFactorGraphLayer> layers;

    public void buildGraph(GameInfo gameInfo, List<Player> players) {
        Object lastOutput = null;

        for(TrueSkillFactorGraphLayer currentLayer : layers)
        {
            if (lastOutput != null) {
                currentLayer.setRawInputVariablesGroups(lastOutput);
            }

            currentLayer.buildLayer(gameInfo, players);

            lastOutput = currentLayer.getRawOutputVariablesGroups();
        }
    }
}
