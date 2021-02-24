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

    @Override
    public String toString() {
        return String.valueOf(user.getId());
    }
}
