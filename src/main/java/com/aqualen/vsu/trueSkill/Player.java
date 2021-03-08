package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Comparable<Player> {
    private User user;
    private Rating rating;
    private int rank;

    @Override
    public int compareTo(Player o) {
        return rank - o.getRank();
    }

    public static User getUserWithUpdatedRating(Player from){
        User user = from.getUser();
        user.setRating(from.getRating().getConservativeRating());

        return user;
    }

    @Override
    public String toString() {
        return String.valueOf(user.getId());
    }
}
