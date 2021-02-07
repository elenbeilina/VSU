package com.aqualen.vsu.trueSkill.factorGraphs.variable;

public class DefaultVariable<TValue> extends Variable<TValue> {
    public DefaultVariable(TValue value) {
        super("Default", value);
    }
}