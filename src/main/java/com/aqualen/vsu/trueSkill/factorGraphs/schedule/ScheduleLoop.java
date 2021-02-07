package com.aqualen.vsu.trueSkill.factorGraphs.schedule;

public class ScheduleLoop<T> extends Schedule<T> {
    private final double maxDelta;
    private final Schedule<T> scheduleToLoop;

    public ScheduleLoop(String name, Schedule<T> scheduleToLoop, double maxDelta) {
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
