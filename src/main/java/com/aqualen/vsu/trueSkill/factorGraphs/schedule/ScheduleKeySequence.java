package com.aqualen.vsu.trueSkill.factorGraphs.schedule;

import java.util.List;

public class ScheduleKeySequence<T extends Schedule> extends Schedule {
    private final List<T> schedules;

    public ScheduleKeySequence(String name, List<T> schedules) {
        super(name);
        this.schedules = schedules;
    }

    @Override
    public double visit(int depth, int maxDepth) {
        double maxDelta = 0;

        for (T currentSchedule : schedules) {
            maxDelta = Math.max(currentSchedule.visit(depth + 1, maxDepth), maxDelta);
        }

        return maxDelta;
    }
}
