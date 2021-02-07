package com.aqualen.vsu.trueSkill.factors;

import com.aqualen.vsu.trueSkill.factorGraphs.Factor;
import com.aqualen.vsu.trueSkill.factorGraphs.Message;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.operatorMultiplication;

public abstract class GaussianFactor extends Factor<GaussianDistribution> {
    protected GaussianFactor(String name) {
        super(name);
    }

    /// Sends the factor-graph message with and returns the log-normalization constant
    @Override
    protected double sendMessage(Message<GaussianDistribution> message,
                                 Variable<GaussianDistribution> variable)
    {
        GaussianDistribution marginal = variable.value;
        GaussianDistribution messageValue = message.value;
        double logZ = GaussianDistribution.logProductNormalization(marginal, messageValue);
        variable.value = operatorMultiplication(marginal, messageValue);
        return logZ;
    }
    @Override
    public Message<GaussianDistribution> createVariableToMessageBinding(
            Variable<GaussianDistribution> variable)
    {
        return createVariableToMessageBinding(variable,
                new Message<>(
                        GaussianDistribution.fromPrecisionMean(0, 0),
                        "message from {0} to {1}", this, variable));
    }
}
