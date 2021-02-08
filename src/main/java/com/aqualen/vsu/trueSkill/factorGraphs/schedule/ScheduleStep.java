package com.aqualen.vsu.trueSkill.factorGraphs.schedule;

import com.aqualen.vsu.trueSkill.factorGraphs.Factor;

public class ScheduleStep<T> extends Schedule {
    private final Factor<T> factor;
    private final int index;

    public ScheduleStep(String name, Factor<T> factor, int index) {
        super(name);
        this.factor = factor;
        this.index = index;
    }

    @Override
    public double visit(int depth, int maxDepth) {
        return factor.updateMessage(index);
    }
}
