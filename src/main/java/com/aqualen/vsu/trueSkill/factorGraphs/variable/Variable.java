package com.aqualen.vsu.trueSkill.factorGraphs.variable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Variable<T> {
    private String name;
    private T prior;

    public Variable(String name, T prior) {
        this.name = "Variable[" + name + "]";
        this.prior = prior;
        ResetToPrior();
    }

    public T value;

    public void ResetToPrior() {
        value = prior;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                '}';
    }
}

