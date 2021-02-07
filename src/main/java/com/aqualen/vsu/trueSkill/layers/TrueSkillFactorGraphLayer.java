package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.trueSkill.FactorGraphs.FactorGraphLayer;
import com.aqualen.vsu.trueSkill.FactorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;

import java.util.List;

public abstract class TrueSkillFactorGraphLayer<Input,Output> extends FactorGraphLayer<Input,Output> {
    public abstract void buildLayer(GameInfo gameInfo, List<Player> players);

    public Schedule<GaussianDistribution> createPriorSchedule() {
        return null;
    }

    public Schedule<GaussianDistribution> createPosteriorSchedule() {
        return null;
    }

}
