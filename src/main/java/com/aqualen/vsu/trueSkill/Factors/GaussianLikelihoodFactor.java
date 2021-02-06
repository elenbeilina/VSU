package com.aqualen.vsu.trueSkill.Factors;

import com.aqualen.vsu.trueSkill.FactorGraphs.Message;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;

import java.util.List;

/// <summary>
/// Connects two variables and adds uncertainty.
/// </summary>
/// <remarks>See the accompanying math paper for more details.</remarks>
public class GaussianLikelihoodFactor extends GaussianFactor {
    private final double _Precision;

    public GaussianLikelihoodFactor(double betaSquared, Variable<GaussianDistribution> variable1,
                                    Variable<GaussianDistribution> variable2) {
        super(String.format("Likelihood of %s going to %s", variable2, variable1));
        _Precision = 1.0 / betaSquared;
        CreateVariableToMessageBinding(variable1);
        CreateVariableToMessageBinding(variable2);
    }

    public double getLogNormalization() {
        return GaussianDistribution.LogRatioNormalization(get_Variables().get(0).Value, get_Messages().get(0).Value);
    }

    private double UpdateHelper(Message<GaussianDistribution> message1, Message<GaussianDistribution> message2,
                                Variable<GaussianDistribution> variable1, Variable<GaussianDistribution> variable2) {
        GaussianDistribution message1Value = new GaussianDistribution(message1.Value);
        GaussianDistribution message2Value = new GaussianDistribution(message2.Value);

        GaussianDistribution marginal1 = new GaussianDistribution(variable1.Value);
        GaussianDistribution marginal2 = new GaussianDistribution(variable2.Value);

        double a = _Precision / (_Precision + marginal2.Precision - message2Value.Precision);

        GaussianDistribution newMessage = GaussianDistribution.FromPrecisionMean(
                a * (marginal2.PrecisionMean - message2Value.PrecisionMean),
                a * (marginal2.Precision - message2Value.Precision));

        GaussianDistribution oldMarginalWithoutMessage = GaussianDistribution.operatorDivision(marginal1, message1Value);

        GaussianDistribution newMarginal = GaussianDistribution.operatorMultiplication(oldMarginalWithoutMessage, newMessage);

        /// Update the message and marginal

        message1.Value = newMessage;
        variable1.Value = newMarginal;

        /// Return the difference in the new marginal
        return GaussianDistribution.operatorMinus(newMarginal, marginal1);
    }

    public double UpdateMessage(int messageIndex) {
        List<Message<GaussianDistribution>> messages = get_Messages();
        List<Variable<GaussianDistribution>> variables = get_Variables();
        switch (messageIndex) {
            case 0:
                return UpdateHelper(messages.get(0), messages.get(1),
                        variables.get(0), variables.get(1));
            case 1:
                return UpdateHelper(messages.get(1), messages.get(0),
                        variables.get(1), variables.get(0));
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }
}