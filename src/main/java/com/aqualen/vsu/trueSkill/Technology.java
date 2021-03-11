package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.Tournament;
import com.aqualen.vsu.enums.TechnologyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Technology {
    private TechnologyName language;
    private Rating rating;
    private int percent;

    public static final BiPredicate<RatingByTechnology, TechnologyName> technologyName =
            (ratingByTechnology, technologyName) -> ratingByTechnology.getTechnology() == technologyName;

    public int getPercent() {
        return percent == 0 ? 1 : percent;
    }


    public static Technology toTechnology(com.aqualen.vsu.entity.Technology technology, List<RatingByTechnology> ratings) {
        RatingByTechnology ratingByTechnology = ratings.stream()
                .filter(byTechnology -> technologyName.test(byTechnology, technology.getTechnology()))
                .findFirst().get();

        return Technology.builder()
                .rating(new Rating(ratingByTechnology.getMean(), ratingByTechnology.getDeviation()))
                .language(technology.getTechnology())
                .percent(technology.getPercent())
                .build();
    }

    @Override
    public String toString() {
        return language.toString();
    }
}
