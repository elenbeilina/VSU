package com.aqualen.vsu.trueSkill.factorGraphs.variable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Variable<T> {
    private String name;
    private T prior;
    public T value;

    public Variable(T prior, String name, Object... args) {
        this.name = "Variable[" + String.format(name, args) + "]";
        this.prior = prior;
        resetToPrior();
    }

    public void resetToPrior() {
        value = prior;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                '}';
    }
}

