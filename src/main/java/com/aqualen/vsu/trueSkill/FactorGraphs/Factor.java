package com.aqualen.vsu.trueSkill.FactorGraphs;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Factor<TValue> {
    private List<Message<TValue>> _Messages = new ArrayList<>();

    private Map<Message<TValue>, Variable<TValue>> _MessageToVariableBinding =
            new HashMap<>();

    private final String _Name;
    private List<Variable<TValue>> _Variables = new ArrayList<>();

    protected Factor(String name) {
        _Name = "Factor[" + name + "]";
    }

    /// Returns the log-normalization constant of that factor
    public double LogNormalization() {
        return 0;
    }

    /// Returns the number of messages that the factor has
    public int NumberOfMessages() {
        return _Messages.size();
    }

    /// Update the message and marginal of the i-th variable that the factor is connected to
    public double UpdateMessage(int messageIndex) {
        if (messageIndex > _Messages.size()) {
            return 0;
        }
        Message<TValue> message = _Messages.get(messageIndex);
        return UpdateMessage(message, _MessageToVariableBinding.get(message));
    }

    protected double UpdateMessage(Message<TValue> message, Variable<TValue> variable) {
        throw new NotImplementedException();
    }

    /// Resets the marginal of the variables a factor is connected to
    public void ResetMarginals() {
        for (Variable<TValue> currentVariable : _MessageToVariableBinding.values()) {
            currentVariable.ResetToPrior();
        }
    }

    /// Sends the ith message to the marginal and returns the log-normalization constant
    public double SendMessage(int messageIndex) {
        if (messageIndex > _Messages.size()) {
            return 0;
        }

        Message<TValue> message = _Messages.get(messageIndex);
        Variable<TValue> variable = _MessageToVariableBinding.get(message);
        return SendMessage(message, variable);
    }

    protected abstract double SendMessage(Message<TValue> message, Variable<TValue> variable);

    public abstract Message<TValue> CreateVariableToMessageBinding(Variable<TValue> variable);

    protected Message<TValue> CreateVariableToMessageBinding(Variable<TValue> variable, Message<TValue> message) {
        _Messages.add(message);
        _MessageToVariableBinding.put(message, variable);
        _Variables.add(variable);

        return message;
    }
}
