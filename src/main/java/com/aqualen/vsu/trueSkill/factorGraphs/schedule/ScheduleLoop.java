package com.aqualen.vsu.trueSkill.factorGraphs.schedule;

public class ScheduleLoop extends Schedule {
    private final double maxDelta;
    private final Schedule scheduleToLoop;

    public ScheduleLoop(String name, Schedule scheduleToLoop, double maxDelta) {
        super(name);
        this.scheduleToLoop = scheduleToLoop;
        this.maxDelta = maxDelta;
    }

    @Override
    public double visit(int depth, int maxDepth) {
        double delta = scheduleToLoop.visit(depth + 1, maxDepth);
        while (delta > maxDelta) {
            delta = scheduleToLoop.visit(depth + 1, maxDepth);
        }

        return delta;
    }
}
