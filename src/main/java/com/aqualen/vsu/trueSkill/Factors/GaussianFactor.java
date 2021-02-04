package com.aqualen.vsu.trueSkill.Factors;

import com.aqualen.vsu.trueSkill.FactorGraphs.Factor;
import com.aqualen.vsu.trueSkill.FactorGraphs.Message;
import com.aqualen.vsu.trueSkill.FactorGraphs.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.operatorMultiplication;

public abstract class GaussianFactor extends Factor<GaussianDistribution> {
    protected GaussianFactor(String name) {
        super(name);
    }

    /// Sends the factor-graph message with and returns the log-normalization constant
    @Override
    protected double SendMessage(Message<GaussianDistribution> message,
                                 Variable<GaussianDistribution> variable)
    {
        GaussianDistribution marginal = variable.Value;
        GaussianDistribution messageValue = message.Value;
        double logZ = GaussianDistribution.LogProductNormalization(marginal, messageValue);
        variable.Value = operatorMultiplication(marginal, messageValue);
        return logZ;
    }
    @Override
    public Message<GaussianDistribution> CreateVariableToMessageBinding(
            Variable<GaussianDistribution> variable)
    {
        return CreateVariableToMessageBinding(variable,
                new Message<>(
                        GaussianDistribution.FromPrecisionMean(0, 0),
                        "message from {0} to {1}", this, variable));
    }
}
