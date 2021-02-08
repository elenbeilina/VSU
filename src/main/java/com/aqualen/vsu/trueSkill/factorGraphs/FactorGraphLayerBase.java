package com.aqualen.vsu.trueSkill.factorGraphs;

import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.Player;

import java.util.List;

public abstract class FactorGraphLayerBase {
    public abstract void buildLayer(GameInfo gameInfo, List<Player> players);

    public Schedule createPriorSchedule() {
        return null;
    }

    public Schedule createPosteriorSchedule() {
        return null;
    }

    public abstract void setRawInputVariablesGroups(Object value);

    public abstract Object getRawOutputVariablesGroups();
}