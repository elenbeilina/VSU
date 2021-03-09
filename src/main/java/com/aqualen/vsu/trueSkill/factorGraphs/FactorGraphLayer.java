package com.aqualen.vsu.trueSkill.factorGraphs;

import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleSequence;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class FactorGraphLayer<Input, Output> extends FactorGraphLayerBase {

    private final List<Factor<GaussianDistribution>> localFactors = new ArrayList<>();
    private final List<List<Output>> outputVariables = new ArrayList<>();
    private List<Input> inputVariables = new ArrayList<>();

    public void addToOutputVariables(List<Output> variables) {
        outputVariables.add(variables);
    }


    public void setRawInputVariablesGroups(Object value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }

        inputVariables = (List<Input>) value;
    }

    public abstract void buildLayer(GameInfo gameInfo, List<Player> players);

    public Object getRawOutputVariablesGroups() {
        return outputVariables;
    }

    protected void addLayerFactor(Factor<GaussianDistribution> factor) {
        localFactors.add(factor);
    }

    protected Schedule scheduleSequence(List<Schedule> itemsToSequence,
                                        String nameFormat,
                                        Object... args) {
        String formattedName = String.format(nameFormat, args);
        return new ScheduleSequence(formattedName, itemsToSequence);
    }
}
