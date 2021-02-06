package com.aqualen.vsu.trueSkill.Factors;

import com.aqualen.vsu.trueSkill.FactorGraphs.Message;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

import java.util.List;

import static com.aqualen.vsu.trueSkill.GaussianDistribution.*;

/// <summary>
/// Connects two variables and adds uncertainty.
/// </summary>
/// <remarks>See the accompanying math paper for more details.</remarks>
public class GaussianLikelihoodFactor extends GaussianFactor {
    private final double precision;

    public GaussianLikelihoodFactor(double betaSquared, Variable<GaussianDistribution> variable1,
                                    Variable<GaussianDistribution> variable2) {
        super(String.format("Likelihood of %s going to %s", variable2, variable1));
        precision = 1.0 / betaSquared;
        createVariableToMessageBinding(variable1);
        createVariableToMessageBinding(variable2);
    }

    public double getLogNormalization() {
        return logRatioNormalization(getVariables().get(0).value, getMessages().get(0).value);
    }

    private double updateHelper(Message<GaussianDistribution> message1, Message<GaussianDistribution> message2,
                                Variable<GaussianDistribution> variable1, Variable<GaussianDistribution> variable2) {
        GaussianDistribution message1Value = new GaussianDistribution(message1.value);
        GaussianDistribution message2Value = new GaussianDistribution(message2.value);

        GaussianDistribution marginal1 = new GaussianDistribution(variable1.value);
        GaussianDistribution marginal2 = new GaussianDistribution(variable2.value);

        double a = precision / (precision + marginal2.precision - message2Value.precision);

        GaussianDistribution newMessage = GaussianDistribution.fromPrecisionMean(
                a * (marginal2.precisionMean - message2Value.precisionMean),
                a * (marginal2.precision - message2Value.precision));

        GaussianDistribution oldMarginalWithoutMessage = operatorDivision(marginal1, message1Value);
        GaussianDistribution newMarginal = operatorMultiplication(oldMarginalWithoutMessage, newMessage);

        /// Update the message and marginal
        message1.value = newMessage;
        variable1.value = newMarginal;

        /// Return the difference in the new marginal
        return GaussianDistribution.operatorMinus(newMarginal, marginal1);
    }

    public double updateMessage(int messageIndex) {
        List<Message<GaussianDistribution>> messages = getMessages();
        List<Variable<GaussianDistribution>> variables = getVariables();
        switch (messageIndex) {
            case 0:
                return updateHelper(messages.get(0), messages.get(1),
                        variables.get(0), variables.get(1));
            case 1:
                return updateHelper(messages.get(1), messages.get(0),
                        variables.get(1), variables.get(0));
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }
}