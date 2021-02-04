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

    /// <summary>
    /// Constructs a rating.
    /// </summary>
    /// <param name="mean">The statistical mean value of the rating (also known as μ).</param>
    /// <param name="standardDeviation">The standard deviation (the spread) of the rating (also known as σ).</param>
    /// <param name="conservativeStandardDeviationMultiplier">The number of <paramref name="standardDeviation"/>s to subtract from the <paramref name="mean"/> to achieve a conservative rating.</param>
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

//    public static Rating GetPartialUpdate(Rating prior, Rating fullPosterior, double updatePercentage)
//    {
//        var priorGaussian = new GaussianDistribution(prior.Mean, prior.StandardDeviation);
//        var posteriorGaussian = new GaussianDistribution(fullPosterior.Mean, fullPosterior.StandardDeviation);
//
//        // From a clarification email from Ralf Herbrich:
//        // "the idea is to compute a linear interpolation between the prior and posterior skills of each player
//        //  ... in the canonical space of parameters"
//
//        double precisionDifference = posteriorGaussian.Precision - priorGaussian.Precision;
//        double partialPrecisionDifference = updatePercentage*precisionDifference;
//
//        double precisionMeanDifference = posteriorGaussian.PrecisionMean - priorGaussian.PrecisionMean;
//        double partialPrecisionMeanDifference = updatePercentage*precisionMeanDifference;
//
//        GaussianDistribution partialPosteriorGaussion = GaussianDistribution.FromPrecisionMean(
//                priorGaussian.PrecisionMean + partialPrecisionMeanDifference,
//                priorGaussian.Precision + partialPrecisionDifference);
//
//        return new Rating(partialPosteriorGaussion.Mean, partialPosteriorGaussion.StandardDeviation,
//                prior.conservativeStandardDeviationMultiplier);
//    }
//
//    public override string ToString()
//{
//    // As a debug helper, display a localized rating:
//    return String.Format(
//            "μ={0:0.0000}, σ={1:0.0000}",
//            Mean, StandardDeviation);
//}
}
