package com.aqualen.vsu.trueSkill.FactorGraphs.schedule;

public abstract class Schedule<T> {
    private final String _Name;

    protected Schedule(String name) {
        _Name = name;
    }

    public abstract double Visit(int depth, int maxDepth);

    public double Visit() {
        return Visit(-1, 0);
    }

    public String

    ToString() {
        return _Name;
    }
}

