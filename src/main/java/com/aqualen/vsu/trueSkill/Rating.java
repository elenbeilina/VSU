package com.aqualen.vsu.trueSkill;

import lombok.Getter;

@Getter
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

    /**
     * Constructs a rating.
     * @param mean - The statistical mean value of the rating (also known as μ)
     * @param standardDeviation - The standard deviation(the spread) of the rating (also known as σ).
     * @param conservativeStandardDeviationMultiplier - The number of standardDeviations to subtract from the mean to achieve a conservative rating.
     */
    public Rating(double conservativeStandardDeviationMultiplier, double mean, double standardDeviation) {
        this.conservativeStandardDeviationMultiplier = conservativeStandardDeviationMultiplier;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }


    /// <summary>
    /// A conservative estimate of skill based on the mean and standard deviation.
    /// </summary>
    public double getConservativeRating()
    {
        return mean - conservativeStandardDeviationMultiplier * standardDeviation;
    }

    public static Rating getPartialUpdate(Rating prior, Rating fullPosterior, double updatePercentage)
    {
        GaussianDistribution priorGaussian = new GaussianDistribution(prior.mean, prior.standardDeviation);
        GaussianDistribution posteriorGaussian = new GaussianDistribution(fullPosterior.mean, fullPosterior.standardDeviation);

        // From a clarification email from Ralf Herbrich:
        // "the idea is to compute a linear interpolation between the prior and posterior skills of each player
        //  ... in the canonical space of parameters"
        double precisionDifference = posteriorGaussian.precision - priorGaussian.precision;
        double partialPrecisionDifference = updatePercentage*precisionDifference;

        double precisionMeanDifference = posteriorGaussian.precisionMean - priorGaussian.precisionMean;
        double partialPrecisionMeanDifference = updatePercentage*precisionMeanDifference;

        GaussianDistribution partialPosteriorGaussion = GaussianDistribution.fromPrecisionMean(
                priorGaussian.precisionMean + partialPrecisionMeanDifference,
                priorGaussian.precision + partialPrecisionDifference);

        return new Rating(partialPosteriorGaussion.mean, partialPosteriorGaussion.standardDeviation,
                prior.conservativeStandardDeviationMultiplier);
    }

    @Override
    public String toString() {
        return String.format(
                "μ={0:0.0000}, σ={1:0.0000}",
                mean, standardDeviation);
    }
}
