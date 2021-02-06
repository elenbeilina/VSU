package com.aqualen.vsu.trueSkill.FactorGraphs.schedule;

import lombok.ToString;

@ToString
public abstract class Schedule<T> {
    private final String name;

    protected Schedule(String name) {
        this.name = name;
    }

    public abstract double visit(int depth, int maxDepth);

    public double visit() {
        return visit(-1, 0);
    }
}

