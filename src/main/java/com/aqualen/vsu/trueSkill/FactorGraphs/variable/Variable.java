package com.aqualen.vsu.trueSkill.FactorGraphs.variable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Variable<T> {
    private String _Name;
    private T _Prior;

    public Variable(String name, T prior) {
        _Name = "Variable[" + name + "]";
        _Prior = prior;
        ResetToPrior();
    }

    public T Value;

    public void ResetToPrior() {
        Value = _Prior;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "_Name='" + _Name + '\'' +
                '}';
    }
}

