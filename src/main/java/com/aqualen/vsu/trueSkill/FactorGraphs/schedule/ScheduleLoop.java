package com.aqualen.vsu.trueSkill.FactorGraphs.schedule;

public class ScheduleLoop<T> extends Schedule<T> {
    private double _MaxDelta;
    private Schedule<T> _ScheduleToLoop;

    public ScheduleLoop(String name, Schedule<T> scheduleToLoop, double maxDelta) {
        super(name);
        _ScheduleToLoop = scheduleToLoop;
        _MaxDelta = maxDelta;
    }

    @Override
    public double Visit(int depth, int maxDepth) {
        int totalIterations = 1;
        double delta = _ScheduleToLoop.Visit(depth + 1, maxDepth);
        while (delta > _MaxDelta) {
            delta = _ScheduleToLoop.Visit(depth + 1, maxDepth);
            totalIterations++;
        }

        return delta;
    }
}
