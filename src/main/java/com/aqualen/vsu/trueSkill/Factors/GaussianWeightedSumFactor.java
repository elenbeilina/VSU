package com.aqualen.vsu.trueSkill.Factors;

import com.aqualen.vsu.trueSkill.FactorGraphs.Message;
import com.aqualen.vsu.trueSkill.FactorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Guard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/// <summary>
/// Factor that sums together multiple Gaussians.
/// </summary>
/// <remarks>See the accompanying math paper for more details.</remarks>
public class GaussianWeightedSumFactor extends GaussianFactor {
    private final List<int[]> variableIndexOrdersForWeights = new ArrayList<>();

    // This following is used for convenience, for example, the first entry is [0, 1, 2]
    // corresponding to v[0] = a1*v[1] + a2*v[2]
    private double[][] weights;
    private double[][] weightsSquared;

    public GaussianWeightedSumFactor(Variable<GaussianDistribution> sumVariable,
                                     List<Variable<GaussianDistribution>> variablesToSum,
                                     double[] variableWeights) {
        super(CreateName(sumVariable, variablesToSum, variableWeights));
        weights = new double[variableWeights.length + 1][];
        weightsSquared = new double[weights.length][];

        // The first weights are a straightforward copy
        // v_0 = a_1*v_1 + a_2*v_2 + ... + a_n * v_n
        weights[0] = new double[variableWeights.length];
        weights[0] = Arrays.copyOf(variableWeights, variableWeights.length);
        weightsSquared[0] = Arrays.stream(weights[0]).map(w -> w * w).toArray();

        // 0..n-1
        variableIndexOrdersForWeights.add(IntStream.range(0, 1 + variablesToSum.size()).toArray());


        // The rest move the variables around and divide out the constant.
        // For example:
        // v_1 = (-a_2 / a_1) * v_2 + (-a3/a1) * v_3 + ... + (1.0 / a_1) * v_0
        // By convention, we'll put the v_0 term at the end

        for (int weightsIndex = 1; weightsIndex < weights.length; weightsIndex++) {
            double[] currentWeights = new double[variableWeights.length];
            weights[weightsIndex] = currentWeights;

            int[] variableIndices = new int[variableWeights.length + 1];
            variableIndices[0] = weightsIndex;

            double[] currentWeightsSquared = new double[variableWeights.length];
            weightsSquared[weightsIndex] = currentWeightsSquared;

            // keep a single variable to keep track of where we are in the array.
            // This is helpful since we skip over one of the spots
            int currentDestinationWeightIndex = 0;

            for (int currentWeightSourceIndex = 0;
                 currentWeightSourceIndex < variableWeights.length;
                 currentWeightSourceIndex++) {
                if (currentWeightSourceIndex == (weightsIndex - 1)) {
                    continue;
                }

                double currentWeight = (-variableWeights[currentWeightSourceIndex] / variableWeights[weightsIndex - 1]);

                if (variableWeights[weightsIndex - 1] == 0) {
                    // HACK: Getting around division by zero
                    currentWeight = 0;
                }

                currentWeights[currentDestinationWeightIndex] = currentWeight;
                currentWeightsSquared[currentDestinationWeightIndex] = currentWeight * currentWeight;

                variableIndices[currentDestinationWeightIndex + 1] = currentWeightSourceIndex + 1;
                currentDestinationWeightIndex++;
            }

            // And the final one
            double finalWeight = 1.0 / variableWeights[weightsIndex - 1];

            if (variableWeights[weightsIndex - 1] == 0) {
                // HACK: Getting around division by zero
                finalWeight = 0;
            }
            currentWeights[currentDestinationWeightIndex] = finalWeight;
            currentWeightsSquared[currentDestinationWeightIndex] = finalWeight * finalWeight;
            variableIndices[variableIndices.length - 1] = 0;
            variableIndexOrdersForWeights.add(variableIndices);
        }

        CreateVariableToMessageBinding(sumVariable);

        for (Variable<GaussianDistribution> currentVariable : variablesToSum) {
            CreateVariableToMessageBinding(currentVariable);
        }
    }

    public double getLogNormalization() {

        List<Variable<GaussianDistribution>> vars = get_Variables();
        List<Message<GaussianDistribution>> messages = get_Messages();

        double result = 0.0;

        // We start at 1 since offset 0 has the sum
        for (int i = 1; i < vars.size(); i++) {
            result += GaussianDistribution.LogRatioNormalization(vars.get(i).Value, messages.get(i).Value);
        }

        return result;
    }

    private double UpdateHelper(double[] weights, double[] weightsSquared,
                                List<Message<GaussianDistribution>> messages,
                                List<Variable<GaussianDistribution>> variables) {
        // Potentially look at http://mathworld.wolfram.com/NormalSumDistribution.html for clues as
        // to what it's doing
        GaussianDistribution message0 = new GaussianDistribution(messages.get(0).Value);
        GaussianDistribution marginal0 = new GaussianDistribution(variables.get(0).Value);

        // The math works out so that 1/newPrecision = sum of a_i^2 /marginalsWithoutMessages[i]
        double inverseOfNewPrecisionSum = 0.0;
        double anotherInverseOfNewPrecisionSum = 0.0;
        double weightedMeanSum = 0.0;
        double anotherWeightedMeanSum = 0.0;

        for (int i = 0; i < weightsSquared.length; i++) {
            // These flow directly from the paper

            inverseOfNewPrecisionSum += weightsSquared[i] /
                    (variables.get(i + 1).Value.Precision - messages.get(i + 1).Value.Precision);

            GaussianDistribution diff = GaussianDistribution.operatorDivision(variables.get(i + 1).Value, messages.get(i + 1).Value);
            anotherInverseOfNewPrecisionSum += weightsSquared[i] / diff.Precision;

            weightedMeanSum += weights[i]
                    *
                    (variables.get(i + 1).Value.PrecisionMean - messages.get(i + 1).Value.PrecisionMean)
                    /
                    (variables.get(i + 1).Value.Precision - messages.get(i + 1).Value.Precision);

            anotherWeightedMeanSum += weights[i] * diff.PrecisionMean / diff.Precision;
        }

        double newPrecision = 1.0 / inverseOfNewPrecisionSum;
        double anotherNewPrecision = 1.0 / anotherInverseOfNewPrecisionSum;

        double newPrecisionMean = newPrecision * weightedMeanSum;
        double anotherNewPrecisionMean = anotherNewPrecision * anotherWeightedMeanSum;

        GaussianDistribution newMessage = GaussianDistribution.FromPrecisionMean(newPrecisionMean, newPrecision);
        GaussianDistribution oldMarginalWithoutMessage = GaussianDistribution.operatorDivision(marginal0, message0);

        GaussianDistribution newMarginal = GaussianDistribution.operatorMultiplication(oldMarginalWithoutMessage, newMessage);

        /// Update the message and marginal

        messages.get(0).Value = newMessage;
        variables.get(0).Value = newMarginal;

        /// Return the difference in the new marginal
        return GaussianDistribution.operatorMinus(newMarginal, marginal0);
    }

    public double UpdateMessage(int messageIndex) {
        List<Message<GaussianDistribution>> allMessages = get_Messages();
        List<Variable<GaussianDistribution>> allVariables = get_Variables();

        Guard.ArgumentIsValidIndex(messageIndex, allMessages.size(), "messageIndex");

        List<Message<GaussianDistribution>> updatedMessages = new ArrayList<>();
        List<Variable<GaussianDistribution>> updatedVariables = new ArrayList<>();

        int[] indicesToUse = variableIndexOrdersForWeights.get(messageIndex);

        // The tricky part here is that we have to put the messages and variables in the same
        // order as the weights. Thankfully, the weights and messages share the same index numbers,
        // so we just need to make sure they're consistent
        for (int i = 0; i < allMessages.size(); i++) {
            updatedMessages.add(allMessages.get(indicesToUse[i]));
            updatedVariables.add(allVariables.get(indicesToUse[i]));
        }

        return UpdateHelper(weights[messageIndex], weightsSquared[messageIndex], updatedMessages, updatedVariables);
    }

    private static String CreateName(Variable<GaussianDistribution> sumVariable,
                                     List<Variable<GaussianDistribution>> variablesToSum, double[] weights) {
        StringBuilder sb = new StringBuilder();
        sb.append(sumVariable.toString());
        sb.append(" = ");
        for (int i = 0; i < variablesToSum.size(); i++) {
            boolean isFirst = (i == 0);

            if (isFirst && (weights[i] < 0)) {
                sb.append("-");
            }

            sb.append(Math.abs(weights[i]));
            sb.append("*[");
            sb.append(variablesToSum.get(i));
            sb.append("]");

            boolean isLast = (i == variablesToSum.size() - 1);

            if (!isLast) {
                if (weights[i + 1] >= 0) {
                    sb.append(" + ");
                } else {
                    sb.append(" - ");
                }
            }
        }

        return sb.toString();
    }
}