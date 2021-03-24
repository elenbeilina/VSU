package com.aqualen.vsu.trueSkill.factors;

import com.aqualen.vsu.trueSkill.factorGraphs.Message;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.*;

/**
 * Factor representing a team difference that has exceeded the draw margin.
 */
public class GaussianGreaterThanFactor extends GaussianFactor {

    public GaussianGreaterThanFactor(Variable<GaussianDistribution> variable) {
        super(String.format("%s", variable));
        createVariableToMessageBinding(variable);
    }

    protected double updateMessage(Message<GaussianDistribution> message,
                                   Variable<GaussianDistribution> variable) {
        GaussianDistribution oldMarginal = new GaussianDistribution(variable.value);
        GaussianDistribution oldMessage = new GaussianDistribution(message.value);
        GaussianDistribution messageFromVar = GaussianDistribution.operatorDivision(oldMarginal, oldMessage);

        double c = messageFromVar.precision;
        double d = messageFromVar.precisionMean;

        double sqrtC = Math.sqrt(c);

        double dOnSqrtC = d / sqrtC;

        double newSqrtC = sqrtC * 0.75;
        double denominator = 1.0 - TruncatedGaussianCorrectionFunctions.WExceedsMargin(dOnSqrtC, newSqrtC);

        double newPrecision = c / denominator;
        double newPrecisionMean = (d +
                sqrtC *
                        TruncatedGaussianCorrectionFunctions.VExceedsMargin(dOnSqrtC, newSqrtC)) /
                denominator;

        GaussianDistribution newMarginal = GaussianDistribution.fromPrecisionMean(newPrecisionMean, newPrecision);

        /// Update the message and marginal
        message.value = operatorDivision(operatorMultiplication(oldMessage, newMarginal), oldMarginal);

        variable.value = newMarginal;

        /// Return the difference in the new marginal
        return operatorMinus(newMarginal, oldMarginal);
    }
}