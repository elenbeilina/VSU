package com.aqualen.vsu.trueSkill;

import lombok.Value;

@Value
public class GameInfo {

    double initialMean;
    double beta;
    double drawProbability;
    double dynamicsFactor;
    double initialStandardDeviation;

    public GameInfo() {
        initialMean = 25.0;
        beta = initialMean / 6.0;
        drawProbability = 0.10;
        dynamicsFactor = initialMean / 300.0;
        initialStandardDeviation = initialMean / 3.0;
    }

    public GameInfo(double initialMean, double beta, double drawProbability,
                    double dynamicsFactor, double initialStandardDeviation) {
        this.initialMean = initialMean;
        this.beta = beta;
        this.drawProbability = drawProbability;
        this.dynamicsFactor = dynamicsFactor;
        this.initialStandardDeviation = initialStandardDeviation;
    }

    public Rating getDefaultRating() {
            return new Rating(initialMean, initialStandardDeviation);
    }
}
