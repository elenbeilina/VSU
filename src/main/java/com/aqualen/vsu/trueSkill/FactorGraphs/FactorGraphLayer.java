package com.aqualen.vsu.trueSkill.FactorGraphs;

import java.util.ArrayList;
import java.util.List;

public abstract class FactorGraphLayer {

    private List<Factor> localFactors = new ArrayList<>();
    private List outputVariablesGroups = new ArrayList();
    private List inputVariablesGroups = new ArrayList();


    public void setRawInputVariablesGroups(Object value)
    {
        if (value == null)
        {
            // TODO: message
            throw new IllegalArgumentException();
        }

        inputVariablesGroups = (List) value;
    }

    public Object getRawOutputVariablesGroups()
    {
        return outputVariablesGroups;
    }

}
