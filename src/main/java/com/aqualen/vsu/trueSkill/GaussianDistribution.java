package com.aqualen.vsu.trueSkill;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GaussianDistribution {
    // Intentionally, we're not going to derive related things, but set them all at once
    // to get around some NaN issues
    public double mean;
    public double standardDeviation;

    /**
     * The Gaussian representation of a flat line.
     **/
    public static final GaussianDistribution GAUSSIAN_LINE = fromPrecisionMean(0, 0);

    // precision and precisionMean are used because they make multiplying and dividing simpler
    // (the the accompanying math paper for more details)
    public double precision;
    public double precisionMean;
    private double variance;

    public GaussianDistribution(double mean, double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        variance = square(this.standardDeviation);
        precision = 1.0 / variance;
        precisionMean = precision * this.mean;
    }

    public GaussianDistribution(GaussianDistribution gaussianDistribution) {
        this.mean = gaussianDistribution.mean;
        this.standardDeviation = gaussianDistribution.standardDeviation;
        this.variance = gaussianDistribution.variance;
        this.precision = gaussianDistribution.precision;
        this.precisionMean = gaussianDistribution.precisionMean;
    }

    public static GaussianDistribution fromPrecisionMean(double precisionMean, double precision) {
        GaussianDistribution gaussianDistribution = new GaussianDistribution();
        gaussianDistribution.precision = precision;
        gaussianDistribution.precisionMean = precisionMean;
        gaussianDistribution.variance = 1.0 / precision;
        gaussianDistribution.standardDeviation = Math.sqrt(gaussianDistribution.variance);
        gaussianDistribution.mean = gaussianDistribution.precisionMean / gaussianDistribution.precision;
        return gaussianDistribution;
    }

    // Although we could use equations from // For details, see http://www.tina-vision.net/tina-knoppix/tina-memo/2003-003.pdf
    // for multiplication, the precision mean ones are easier to write :)
    public static GaussianDistribution operatorMultiplication(GaussianDistribution left, GaussianDistribution right) {
        return fromPrecisionMean(left.precisionMean + right.precisionMean, left.precision + right.precision);
    }

    /// Computes the absolute difference between two Gaussians
    public static double absoluteDifference(GaussianDistribution left, GaussianDistribution right) {
        return Math.max(
                Math.abs(left.precisionMean - right.precisionMean),
                Math.sqrt(Math.abs(left.precision - right.precision)));
    }

    /// Computes the absolute difference between two Gaussians
    public static double operatorMinus(GaussianDistribution left, GaussianDistribution right) {
        return absoluteDifference(left, right);
    }

    public static double logProductNormalization(GaussianDistribution left, GaussianDistribution right) {
        if ((left.precision == 0) || (right.precision == 0)) {
            return 0;
        }

        double varianceSum = left.variance + right.variance;
        double meanDifference = left.mean - right.mean;

        double logSqrt2Pi = Math.log(Math.sqrt(2 * Math.PI));
        return -logSqrt2Pi - (Math.log(varianceSum) / 2.0) - (square(meanDifference) / (2.0 * varianceSum));
    }


    public static GaussianDistribution operatorDivision(GaussianDistribution numerator, GaussianDistribution denominator) {
        return fromPrecisionMean(numerator.precisionMean - denominator.precisionMean,
                numerator.precision - denominator.precision);
    }

    public static double logRatioNormalization(GaussianDistribution numerator, GaussianDistribution denominator) {
        if ((numerator.precision == 0) || (denominator.precision == 0)) {
            return 0;
        }

        double varianceDifference = denominator.variance - numerator.variance;
        double meanDifference = numerator.mean - denominator.mean;

        double logSqrt2Pi = Math.log(Math.sqrt(2 * Math.PI));

        return Math.log(denominator.variance) + logSqrt2Pi - Math.log(varianceDifference) / 2.0 +
                square(meanDifference) / (2 * varianceDifference);
    }

    public static double square(double x) {
        return x * x;
    }

    public static double at(double x) {
        return at(x, 0, 1);
    }

    public static double at(double x, double mean, double standardDeviation) {
        // See http://mathworld.wolfram.com/NormalDistribution.html
        //                1              -(x-mean)^2 / (2*stdDev^2)
        // P(x) = ------------------- * e
        //        stdDev * sqrt(2*pi)

        double multiplier = 1.0 / (standardDeviation * Math.sqrt(2 * Math.PI));
        double expPart = Math.exp((-1.0 * Math.pow(x - mean, 2.0)) / (2 * (standardDeviation * standardDeviation)));
        double result = multiplier * expPart;
        return result;
    }

    public static double cumulativeTo(double x, double mean, double standardDeviation) {
        double invsqrt2 = -0.707106781186547524400844362104;
        double result = errorFunctionCumulativeTo(invsqrt2 * x);
        return 0.5 * result;
    }

    public static double cumulativeTo(double x) {
        return cumulativeTo(x, 0, 1);
    }

    private static double errorFunctionCumulativeTo(double x) {
        // Derived from page 265 of Numerical Recipes 3rd Edition
        double z = Math.abs(x);

        double t = 2.0 / (2.0 + z);
        double ty = 4 * t - 2;

        double[] coefficients = {
                -1.3026537197817094, 6.4196979235649026e-1,
                1.9476473204185836e-2, -9.561514786808631e-3, -9.46595344482036e-4,
                3.66839497852761e-4, 4.2523324806907e-5, -2.0278578112534e-5,
                -1.624290004647e-6, 1.303655835580e-6, 1.5626441722e-8, -8.5238095915e-8,
                6.529054439e-9, 5.059343495e-9, -9.91364156e-10, -2.27365122e-10,
                9.6467911e-11, 2.394038e-12, -6.886027e-12, 8.94487e-13, 3.13092e-13,
                -1.12708e-13, 3.81e-16, 7.106e-15, -1.523e-15, -9.4e-17, 1.21e-16, -2.8e-17
        };

        int ncof = coefficients.length;
        double d = 0.0;
        double dd = 0.0;


        for (int j = ncof - 1; j > 0; j--) {
            double tmp = d;
            d = ty * d - dd + coefficients[j];
            dd = tmp;
        }

        double ans = t * Math.exp(-z * z + 0.5 * (coefficients[0] + ty * d) - dd);
        return x >= 0.0 ? ans : (2.0 - ans);
    }


    private static double inverseErrorFunctionCumulativeTo(double p) {
        // From page 265 of numerical recipes

        if (p >= 2.0) {
            return -100;
        }
        if (p <= 0.0) {
            return 100;
        }

        double pp = (p < 1.0) ? p : 2 - p;
        double t = Math.sqrt(-2 * Math.log(pp / 2.0)); // Initial guess
        double x = -0.70711 * ((2.30753 + t * 0.27061) / (1.0 + t * (0.99229 + t * 0.04481)) - t);

        for (int j = 0; j < 2; j++) {
            double err = errorFunctionCumulativeTo(x) - pp;
            x += err / (1.12837916709551257 * Math.exp(-(x * x)) - x * err); // Halley
        }

        return p < 1.0 ? x : -x;
    }

    public static double inverseCumulativeTo(double x, double mean, double standardDeviation) {
        // From numerical recipes, page 320
        return mean - Math.sqrt(2) * standardDeviation * inverseErrorFunctionCumulativeTo(2 * x);
    }

    @Override
    public String toString() {
        return String.format("μ=%s, σ=%s",
                mean,
                standardDeviation);
    }
}
