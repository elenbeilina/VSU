package com.aqualen.vsu.trueSkill.factors;

import com.aqualen.vsu.trueSkill.GaussianDistribution;

public class TruncatedGaussianCorrectionFunctions
    {
        // These functions from the bottom of page 4 of the TrueSkill paper.

        /// <summary>
        /// The "V" function where the team performance difference is greater than the draw margin.
        /// </summary>
        /// <remarks>In the reference F# implementation, this is referred to as "the additive 
        /// correction of a single-sided truncated Gaussian with unit variance."</remarks>
        /// <param name="teamPerformanceDifference"></param>
        /// <param name="drawMargin">In the paper, it's referred to as just "ε".</param>
        /// <returns></returns>
        public static double VExceedsMargin(double teamPerformanceDifference, double drawMargin)
        {
            double denominator = GaussianDistribution.cumulativeTo(teamPerformanceDifference - drawMargin);

            if (denominator < 2.222758749e-162)
            {
                return -teamPerformanceDifference + drawMargin;
            }

            return GaussianDistribution.at(teamPerformanceDifference - drawMargin)/denominator;
        }

        /// <summary>
        /// The "W" function where the team performance difference is greater than the draw margin.
        /// </summary>
        /// <remarks>In the reference F# implementation, this is referred to as "the multiplicative 
        /// correction of a single-sided truncated Gaussian with unit variance."</remarks>
        /// <param name="teamPerformanceDifference"></param>
        /// <param name="drawMargin"></param>
        /// <param name="c"></param>
        /// <returns></returns>
        public static double WExceedsMargin(double teamPerformanceDifference, double drawMargin, double c)
        {
            return WExceedsMargin(teamPerformanceDifference/c, drawMargin/c);
            //var vWin = VExceedsMargin(teamPerformanceDifference, drawMargin, c);
            //return vWin * (vWin + (teamPerformanceDifference - drawMargin) / c);
        }

        public static double WExceedsMargin(double teamPerformanceDifference, double drawMargin)
        {
            double denominator = GaussianDistribution.cumulativeTo(teamPerformanceDifference - drawMargin);

            if (denominator < 2.222758749e-162)
            {
                if (teamPerformanceDifference < 0.0)
                {
                    return 1.0;
                }
                return 0.0;
            }

            double vWin = VExceedsMargin(teamPerformanceDifference, drawMargin);
            return vWin*(vWin + teamPerformanceDifference - drawMargin);
        }

        // the additive correction of a double-sided truncated Gaussian with unit variance
        public static double VWithinMargin(double teamPerformanceDifference, double drawMargin, double c)
        {
            return VWithinMargin(teamPerformanceDifference/c, drawMargin/c);
            //var teamPerformanceDifferenceAbsoluteValue = Math.Abs(teamPerformanceDifference);
            //return (GaussianDistribution.at((-drawMargin - teamPerformanceDifferenceAbsoluteValue) / c) - GaussianDistribution.at((drawMargin - teamPerformanceDifferenceAbsoluteValue) / c))
            //       /
            //       (GaussianDistribution.cumulativeTo((drawMargin - teamPerformanceDifferenceAbsoluteValue) / c) - GaussianDistribution.cumulativeTo((-drawMargin - teamPerformanceDifferenceAbsoluteValue) / c));
        }

        // My original:
        //public static double VWithinMargin(double teamPerformanceDifference, double drawMargin)
        //{
        //    var teamPerformanceDifferenceAbsoluteValue = Math.Abs(teamPerformanceDifference);
        //    return (GaussianDistribution.at(-drawMargin - teamPerformanceDifferenceAbsoluteValue) - GaussianDistribution.at(drawMargin - teamPerformanceDifferenceAbsoluteValue))
        //           /
        //           (GaussianDistribution.cumulativeTo(drawMargin - teamPerformanceDifferenceAbsoluteValue) - GaussianDistribution.cumulativeTo(-drawMargin - teamPerformanceDifferenceAbsoluteValue));
        //}

        // from F#:
        public static double VWithinMargin(double teamPerformanceDifference, double drawMargin)
        {
            double teamPerformanceDifferenceAbsoluteValue = Math.abs(teamPerformanceDifference);
            double denominator =
                GaussianDistribution.cumulativeTo(drawMargin - teamPerformanceDifferenceAbsoluteValue) -
                GaussianDistribution.cumulativeTo(-drawMargin - teamPerformanceDifferenceAbsoluteValue);
            if (denominator < 2.222758749e-162)
            {
                if (teamPerformanceDifference < 0.0)
                {
                    return -teamPerformanceDifference - drawMargin;
                }

                return -teamPerformanceDifference + drawMargin;
            }

            double numerator = GaussianDistribution.at(-drawMargin - teamPerformanceDifferenceAbsoluteValue) -
                               GaussianDistribution.at(drawMargin - teamPerformanceDifferenceAbsoluteValue);

            if (teamPerformanceDifference < 0.0)
            {
                return -numerator/denominator;
            }

            return numerator/denominator;
        }

        // the multiplicative correction of a double-sided truncated Gaussian with unit variance
        public static double WWithinMargin(double teamPerformanceDifference, double drawMargin, double c)
        {
            return WWithinMargin(teamPerformanceDifference/c, drawMargin/c);
            //var teamPerformanceDifferenceAbsoluteValue = Math.Abs(teamPerformanceDifference);
            //var vDraw = VWithinMargin(teamPerformanceDifferenceAbsoluteValue, drawMargin, c);

            //return (vDraw * vDraw)
            //       +
            //       (
            //        (
            //            (
            //                ((drawMargin - teamPerformanceDifferenceAbsoluteValue) / c)
            //                *
            //                GaussianDistribution.at((drawMargin - teamPerformanceDifferenceAbsoluteValue) / c)
            //            )
            //            +
            //            (
            //                ((drawMargin + teamPerformanceDifferenceAbsoluteValue) / c)
            //                *
            //                GaussianDistribution.at((drawMargin + teamPerformanceDifferenceAbsoluteValue) / c)
            //            )
            //        )
            //        /
            //        (
            //            GaussianDistribution.cumulativeTo((drawMargin - teamPerformanceDifferenceAbsoluteValue) / c)
            //            -
            //            GaussianDistribution.cumulativeTo((-drawMargin - teamPerformanceDifferenceAbsoluteValue) / c)
            //        )
            //    );
        }

        // My original:
        //public static double WWithinMargin(double teamPerformanceDifference, double drawMargin)
        //{
        //    var teamPerformanceDifferenceAbsoluteValue = Math.Abs(teamPerformanceDifference);
        //    var vDraw = VWithinMargin(teamPerformanceDifferenceAbsoluteValue, drawMargin);
        //    return (vDraw * vDraw)
        //           +
        //           (
        //                ((drawMargin - teamPerformanceDifferenceAbsoluteValue) * GaussianDistribution.at(drawMargin - teamPerformanceDifferenceAbsoluteValue) + (drawMargin + teamPerformanceDifferenceAbsoluteValue) * GaussianDistribution.at(drawMargin + teamPerformanceDifferenceAbsoluteValue))
        //                /
        //                (GaussianDistribution.cumulativeTo(drawMargin - teamPerformanceDifferenceAbsoluteValue) - GaussianDistribution.cumulativeTo(-drawMargin - teamPerformanceDifferenceAbsoluteValue))
        //           );
        //}

        // From F#:
        public static double WWithinMargin(double teamPerformanceDifference, double drawMargin)
        {
            double teamPerformanceDifferenceAbsoluteValue = Math.abs(teamPerformanceDifference);
            double denominator = GaussianDistribution.cumulativeTo(drawMargin - teamPerformanceDifferenceAbsoluteValue)
                                 -
                                 GaussianDistribution.cumulativeTo(-drawMargin - teamPerformanceDifferenceAbsoluteValue);

            if (denominator < 2.222758749e-162)
            {
                return 1.0;
            }

            double vt = VWithinMargin(teamPerformanceDifferenceAbsoluteValue, drawMargin);

            return vt*vt +
                   (
                       (drawMargin - teamPerformanceDifferenceAbsoluteValue)
                       *
                       GaussianDistribution.at(
                           drawMargin - teamPerformanceDifferenceAbsoluteValue)
                       - (-drawMargin - teamPerformanceDifferenceAbsoluteValue)
                         *
                         GaussianDistribution.at(-drawMargin - teamPerformanceDifferenceAbsoluteValue))/denominator;
        }
    }