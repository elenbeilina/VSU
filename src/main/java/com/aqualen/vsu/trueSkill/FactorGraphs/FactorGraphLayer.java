package com.aqualen.vsu.trueSkill.FactorGraphs;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class FactorGraphLayer {

    private List<Factor> localFactors = new ArrayList<>();
    private final List<KeyedVariable<User, GaussianDistribution>> outputVariables = new ArrayList<>();
    private List<KeyedVariable<User, GaussianDistribution>> inputVariables = new ArrayList<>();

    public void addToOutputVariables(KeyedVariable<User, GaussianDistribution> variable){
        outputVariables.add(variable);
    }


    public void setRawInputVariablesGroups(Object value)
    {
        if (value == null)
        {
            // TODO: message
            throw new IllegalArgumentException();
        }

        inputVariables = (List) value;
    }

    public Object getRawOutputVariablesGroups()
    {
        return outputVariables;
    }

    protected void addLayerFactor(Factor factor)
    {
        localFactors.add(factor);
    }
}
