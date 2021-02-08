package com.aqualen.vsu.trueSkill.factors;

import com.aqualen.vsu.trueSkill.factorGraphs.Message;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.*;

/**
 * Factor representing a team difference that has exceeded the draw margin.
 */
public class GaussianGreaterThanFactor extends GaussianFactor {

    private final double epsilon;

    public GaussianGreaterThanFactor(Variable<GaussianDistribution> variable, double epsilon) {
        super(String.format("%s", variable));
        this.epsilon = epsilon;
        createVariableToMessageBinding(variable);
    }


    public double getLogNormalization() {
        GaussianDistribution marginal = getVariables().get(0).value;
        GaussianDistribution message = getMessages().get(0).value;
        GaussianDistribution messageFromVariable = operatorDivision(marginal,message);
        return -GaussianDistribution.logProductNormalization(messageFromVariable, message)
                +
                Math.log(
                        GaussianDistribution.cumulativeTo((messageFromVariable.mean - epsilon) /
                                messageFromVariable.standardDeviation));
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

        double epsilsonTimesSqrtC = epsilon * sqrtC;
        d = messageFromVar.precisionMean;

        double denom = 1.0 - TruncatedGaussianCorrectionFunctions.WExceedsMargin(dOnSqrtC, epsilsonTimesSqrtC);

        double newPrecision = c / denom;
        double newPrecisionMean = (d +
                sqrtC *
                        TruncatedGaussianCorrectionFunctions.VExceedsMargin(dOnSqrtC, epsilsonTimesSqrtC)) /
                denom;

        GaussianDistribution newMarginal = GaussianDistribution.fromPrecisionMean(newPrecisionMean, newPrecision);
        GaussianDistribution newMessage = operatorDivision(operatorMultiplication(oldMessage, newMarginal), oldMarginal);

        /// Update the message and marginal
        message.value = newMessage;

        variable.value = newMarginal;

        /// Return the difference in the new marginal
        return operatorMinus(newMarginal, oldMarginal);
    }
}