package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.Player;

import java.util.List;

public interface TrueSkillFactorGraphLayer {
    void setRawInputVariablesGroups(Object lastOutput);
    void buildLayer(GameInfo gameInfo, List<Player> players);
    Object getRawOutputVariablesGroups();
}
