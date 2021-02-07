package com.aqualen.vsu.trueSkill.factorGraphs;

import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleSequence;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class FactorGraphLayer<Input, Output> extends FactorGraphLayerBase<GaussianDistribution> {

    private final List<Factor<GaussianDistribution>> localFactors = new ArrayList<>();
    private final List<Output> outputVariables = new ArrayList<>();
    private List<Input> inputVariables = new ArrayList<>();

    public void addToOutputVariables(Output variable) {
        outputVariables.add(variable);
    }


    public void setRawInputVariablesGroups(Object value) {
        if (value == null) {
            // TODO: message
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

    protected Schedule<GaussianDistribution> scheduleSequence(List<Schedule<GaussianDistribution>> itemsToSequence,
                                                              String nameFormat,
                                                              Object... args) {
        String formattedName = String.format(nameFormat, args);
        return new ScheduleSequence<>(formattedName, itemsToSequence);
    }
}
