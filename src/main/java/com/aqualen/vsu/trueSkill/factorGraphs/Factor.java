package com.aqualen.vsu.trueSkill.factorGraphs;

import com.aqualen.vsu.exceptions.ReadableException;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.Variable;
import lombok.Getter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public abstract class Factor<TValue> {
    private final List<Message<TValue>> messages = new ArrayList<>();
    private final Map<Message<TValue>, Variable<TValue>> messageToVariableBinding =
            new HashMap<>();

    private final String name;
    private final List<Variable<TValue>> variables = new ArrayList<>();

    protected Factor(String name) {
        this.name = "Factor[" + name + "]";
    }

    /// Update the message and marginal of the i-th variable that the factor is connected to
    public double updateMessage(int messageIndex) {
        if (messageIndex > messages.size()) {
            return 0;
        }
        Message<TValue> message = messages.get(messageIndex);
        return updateMessage(message, messageToVariableBinding.get(message));
    }

    protected double updateMessage(Message<TValue> message, Variable<TValue> variable) {
        throw new ReadableException("Not implemented!");
    }

    protected abstract double sendMessage(Message<TValue> message, Variable<TValue> variable);

    public abstract void createVariableToMessageBinding(Variable<TValue> variable);

    protected void createVariableToMessageBinding(Variable<TValue> variable, Message<TValue> message) {
        messages.add(message);
        messageToVariableBinding.put(message, variable);
        variables.add(variable);

    }
}
