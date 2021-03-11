package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.RatingByTechnology;
import com.aqualen.vsu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.aqualen.vsu.trueSkill.Technology.technologyName;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Comparable<Player> {
    private User user;
    private List<Technology> skills;
    private int rank;

    public static User getUserWithUpdatedRating(Player from) {
        User user = from.getUser();

        for (Technology technology : from.skills) {
            for (RatingByTechnology ratingByTechnology : user.getRatings()) {

                if (technologyName.test(ratingByTechnology, technology.getLanguage())) {
                    Rating rating = technology.getRating();
                    ratingByTechnology.setMean(rating.getMean());
                    ratingByTechnology.setDeviation(rating.getStandardDeviation());
                }
            }
        }
        return user;
    }

    @Override
    public int compareTo(Player o) {
        return rank - o.getRank();
    }

    @Override
    public String toString() {
        return String.valueOf(user.getId());
    }
}
