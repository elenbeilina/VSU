package com.aqualen.vsu.trueSkill.FactorGraphs.schedule;

import com.aqualen.vsu.trueSkill.FactorGraphs.Factor;

public class ScheduleStep<T> extends Schedule<T> {
    private Factor<T> _Factor;
    private
    int _Index;

    public ScheduleStep(String name, Factor<T> factor, int index) {

        super(name);
        _Factor = factor;
        _Index = index;
    }

    @Override
    public double Visit(int depth, int maxDepth) {
        return _Factor.UpdateMessage(_Index);
    }
}
