package com.aqualen.vsu.trueSkill.Factors;

import com.aqualen.vsu.trueSkill.FactorGraphs.Message;
import com.aqualen.vsu.trueSkill.FactorGraphs.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.*;

/// <summary>
/// Factor representing a team difference that has exceeded the draw margin.
/// </summary>
/// <remarks>See the accompanying math paper for more details.</remarks>
public class GaussianGreaterThanFactor extends GaussianFactor {

    private final double epsilon;

    public GaussianGreaterThanFactor(Variable<GaussianDistribution> variable, double epsilon) {
        super(String.format("%s", variable));
        this.epsilon = epsilon;
        CreateVariableToMessageBinding(variable);
    }


    public double getLogNormalization() {
        GaussianDistribution marginal = get_Variables().get(0).Value;
        GaussianDistribution message = get_Messages().get(0).Value;
        GaussianDistribution messageFromVariable = operatorDivision(marginal,message);
        return -GaussianDistribution.LogProductNormalization(messageFromVariable, message)
                +
                Math.log(
                        GaussianDistribution.CumulativeTo((messageFromVariable.Mean - epsilon) /
                                messageFromVariable.StandardDeviation));
    }

    protected double UpdateMessage(Message<GaussianDistribution> message,
                                   Variable<GaussianDistribution> variable) {
        GaussianDistribution oldMarginal = new GaussianDistribution(variable.Value);
        GaussianDistribution oldMessage = new GaussianDistribution(message.Value);
        GaussianDistribution messageFromVar = GaussianDistribution.operatorDivision(oldMarginal, oldMessage);

        double c = messageFromVar.Precision;
        double d = messageFromVar.PrecisionMean;

        double sqrtC = Math.sqrt(c);

        double dOnSqrtC = d / sqrtC;

        double epsilsonTimesSqrtC = epsilon * sqrtC;
        d = messageFromVar.PrecisionMean;

        double denom = 1.0 - TruncatedGaussianCorrectionFunctions.WExceedsMargin(dOnSqrtC, epsilsonTimesSqrtC);

        double newPrecision = c / denom;
        double newPrecisionMean = (d +
                sqrtC *
                        TruncatedGaussianCorrectionFunctions.VExceedsMargin(dOnSqrtC, epsilsonTimesSqrtC)) /
                denom;

        GaussianDistribution newMarginal = GaussianDistribution.FromPrecisionMean(newPrecisionMean, newPrecision);
        GaussianDistribution newMessage = operatorDivision(operatorMultiplication(oldMessage, newMarginal), oldMarginal);

        /// Update the message and marginal
        message.Value = newMessage;

        variable.Value = newMarginal;

        /// Return the difference in the new marginal
        return operatorMinus(newMarginal, oldMarginal);
    }
}