package com.aqualen.vsu.trueSkill.factorGraphs.schedule;

import java.util.List;

public class ScheduleKeySequence<TValue, TSchedule extends Schedule<TValue>> extends Schedule<TValue> {
    private final List<TSchedule> schedules;

    public ScheduleKeySequence(String name, List<TSchedule> schedules) {
        super(name);
        this.schedules = schedules;
    }

    @Override
    public double visit(int depth, int maxDepth) {
        double maxDelta = 0;

        for (TSchedule currentSchedule : schedules) {
            maxDelta = Math.max(currentSchedule.visit(depth + 1, maxDepth), maxDelta);
        }

        return maxDelta;
    }
}
