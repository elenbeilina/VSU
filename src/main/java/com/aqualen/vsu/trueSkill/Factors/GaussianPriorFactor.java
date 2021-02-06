package com.aqualen.vsu.trueSkill.Factors;

import com.aqualen.vsu.trueSkill.FactorGraphs.Message;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

/// <summary>
/// Supplies the factor graph with prior information.
/// </summary>
/// <remarks>See the accompanying math paper for more details.</remarks>
public class GaussianPriorFactor extends GaussianFactor {
    private final GaussianDistribution newMessage;

    public GaussianPriorFactor(double mean, double variance, Variable<GaussianDistribution> variable) {
        super(String.format("Prior value going to %s", variable));
        newMessage = new GaussianDistribution(mean, Math.sqrt(variance));
        createVariableToMessageBinding(variable,
                new Message<>(
                        GaussianDistribution.fromPrecisionMean(0, 0),
                        "message from %s to %s",
                        this, variable));
    }

    protected double updateMessage(Message<GaussianDistribution> message,
                                   Variable<GaussianDistribution> variable) {
        GaussianDistribution oldMarginal = new GaussianDistribution(variable.value);
        GaussianDistribution newMarginal =
                GaussianDistribution.fromPrecisionMean(
                        oldMarginal.precisionMean + newMessage.precisionMean - message.value.precisionMean,
                        oldMarginal.precision + newMessage.precision - message.value.precision);
        variable.value = newMarginal;
        message.value = newMessage;
        return GaussianDistribution.operatorMinus(oldMarginal, newMarginal);
    }
}
