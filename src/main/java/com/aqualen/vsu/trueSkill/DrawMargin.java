package com.aqualen.vsu.trueSkill;

public class DrawMargin {

    /**
     * Derived from TrueSkill technical report (MSR-TR-2006-80), page 6
     *
     * @param drawProbability = 2 * CDF(margin/(sqrt(n1+n2)*beta)) -1
     *                        implies
     * @return margin = inversecdf((draw probability + 1)/2) * sqrt(n1+n2) * beta
     * n1 and n2 are the number of players on each team
     */
    public static double GetDrawMarginFromDrawProbability(double drawProbability, double beta) {
        return GaussianDistribution.inverseCumulativeTo(.5 * (drawProbability + 1), 0, 1) * Math.sqrt(1 + 1) *
                beta;
    }
}