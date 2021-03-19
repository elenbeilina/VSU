package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.enums.TechnologyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.BiPredicate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Technology {
    private TechnologyName language;
    private Rating rating;
    private double percent;

    public static final BiPredicate<RatingByTechnology, TechnologyName> technologyName =
            (ratingByTechnology, technologyName) -> ratingByTechnology.extractTechnology() == technologyName;

    public double getPercent() {
        return percent == 0 ? 1 : percent/100;
    }


    public static Technology toTechnology(com.aqualen.vsu.entity.Technology technology, List<RatingByTechnology> ratings) {
        RatingByTechnology ratingByTechnology = ratings.stream()
                .filter(rating -> technologyName.test(rating, technology.extractTechnology()))
                .findFirst().get();

        return Technology.builder()
                .rating(new Rating(ratingByTechnology.getMean(), ratingByTechnology.getDeviation()))
                .language(technology.extractTechnology())
                .percent(technology.getPercent())
                .build();
    }

    @Override
    public String toString() {
        return language.toString();
    }
}
