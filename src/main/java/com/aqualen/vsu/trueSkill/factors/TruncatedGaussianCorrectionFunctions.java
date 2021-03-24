package com.aqualen.vsu.trueSkill.factors;

import com.aqualen.vsu.trueSkill.GaussianDistribution;

public class TruncatedGaussianCorrectionFunctions {

    /**
     * Mean Additive Truncated Gaussian Function: “v”
     *
     * @param teamPerformanceDifference Difference of the teams.
     * @param drawMargin                In the paper, it's referred to as just "ε".
     * @return double
     */
    public static double VExceedsMargin(double teamPerformanceDifference, double drawMargin) {
        double denominator = GaussianDistribution.cumulativeTo(teamPerformanceDifference - drawMargin);

        return GaussianDistribution.at(teamPerformanceDifference - drawMargin) / denominator;
    }

    /**
     * Variance Multiplicative Function: “w”
     */
    public static double WExceedsMargin(double teamPerformanceDifference, double drawMargin) {
        double vWin = VExceedsMargin(teamPerformanceDifference, drawMargin);
        return vWin * (vWin + teamPerformanceDifference - drawMargin);
    }
}