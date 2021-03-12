package com.aqualen.vsu.trueSkill;

import lombok.Data;

@Data
public class Rating {
    private final double conservativeStandardDeviationMultiplier;
    private final double mean;
    private final double standardDeviation;

    /**
     * Constructs a rating.
     * @param mean - The statistical mean value of the rating (also known as μ)
     * @param standardDeviation - The standard deviation(the spread) of the rating (also known as σ).
     */
    public Rating(double mean, double standardDeviation){
        this.conservativeStandardDeviationMultiplier = 3;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    /** A conservative estimate of skill based on the mean and standard deviation.*/
    public double getConservativeRating() {
        return mean - conservativeStandardDeviationMultiplier * standardDeviation;
    }

    @Override
    public String toString() {
        return String.format(
                "μ=%s, σ=%s",
                mean, standardDeviation);
    }
}
