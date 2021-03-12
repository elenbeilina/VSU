package com.aqualen.vsu.trueSkill.factors;

import com.aqualen.vsu.trueSkill.GaussianDistribution;

public class TruncatedGaussianCorrectionFunctions {

    /**
     * The "V" function where the team performance difference is greater than the draw margin. In
     * the reference F# implementation, this is referred to as "the additive correction of a
     * single-sided truncated Gaussian with unit variance."
     *
     * @param teamPerformanceDifference Difference of the teams.
     * @param drawMargin                In the paper, it's referred to as just "ε".
     * @return double
     */
    public static double VExceedsMargin(double teamPerformanceDifference, double drawMargin) {
        double denominator = GaussianDistribution.cumulativeTo(teamPerformanceDifference - drawMargin);

        if (denominator < 2.222758749e-162) {
            return -teamPerformanceDifference + drawMargin;
        }

        return GaussianDistribution.at(teamPerformanceDifference - drawMargin) / denominator;
    }

    /**
     * The "W" function where the team performance difference is greater than the draw margin.
     * In the reference F# implementation, this is referred to as "the multiplicative
     * correction of a single-sided truncated Gaussian with unit variance.
     */
    public static double WExceedsMargin(double teamPerformanceDifference, double drawMargin) {
        double denominator = GaussianDistribution.cumulativeTo(teamPerformanceDifference - drawMargin);

        if (denominator < 2.222758749e-162) {
            if (teamPerformanceDifference < 0.0) {
                return 1.0;
            }
            return 0.0;
        }

        double vWin = VExceedsMargin(teamPerformanceDifference, drawMargin);
        return vWin * (vWin + teamPerformanceDifference - drawMargin);
    }
}