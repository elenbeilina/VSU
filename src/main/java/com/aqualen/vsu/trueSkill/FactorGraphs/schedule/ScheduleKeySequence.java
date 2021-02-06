package com.aqualen.vsu.trueSkill.FactorGraphs.schedule;

import java.util.List;

public class ScheduleKeySequence<TValue, TSchedule extends Schedule<TValue>> extends Schedule<TValue> {
    private final List<TSchedule> _Schedules;

    public ScheduleKeySequence(String name, List<TSchedule> schedules) {
        super(name);
        _Schedules = schedules;
    }

    @Override
    public double Visit(int depth, int maxDepth) {
        double maxDelta = 0;

        for (TSchedule currentSchedule : _Schedules) {
            maxDelta = Math.max(currentSchedule.Visit(depth + 1, maxDepth), maxDelta);
        }

        return maxDelta;
    }
}
