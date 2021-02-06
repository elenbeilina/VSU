package com.aqualen.vsu.trueSkill.Factors;

import com.aqualen.vsu.trueSkill.FactorGraphs.Message;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

/// <summary>
/// Supplies the factor graph with prior information.
/// </summary>
/// <remarks>See the accompanying math paper for more details.</remarks>
public class GaussianPriorFactor extends GaussianFactor {
    private final GaussianDistribution _NewMessage;

    public GaussianPriorFactor(double mean, double variance, Variable<GaussianDistribution> variable) {
        super(String.format("Prior value going to %s", variable));
        _NewMessage = new GaussianDistribution(mean, Math.sqrt(variance));
        CreateVariableToMessageBinding(variable,
                new Message<>(
                        GaussianDistribution.FromPrecisionMean(0, 0),
                        "message from %s to %s",
                        this, variable));
    }

    protected double UpdateMessage(Message<GaussianDistribution> message,
                                   Variable<GaussianDistribution> variable) {
        GaussianDistribution oldMarginal = new GaussianDistribution(variable.Value);
        GaussianDistribution newMarginal =
                GaussianDistribution.FromPrecisionMean(
                        oldMarginal.PrecisionMean + _NewMessage.PrecisionMean - message.Value.PrecisionMean,
                        oldMarginal.Precision + _NewMessage.Precision - message.Value.Precision);
        variable.Value = newMarginal;
        message.Value = _NewMessage;
        return GaussianDistribution.operatorMinus(oldMarginal, newMarginal);
    }
}
