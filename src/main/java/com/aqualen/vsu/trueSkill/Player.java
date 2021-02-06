package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player implements Comparable<Player>{
    User user;
    Rating rating;
    int rank;

    @Override
    public int compareTo(Player o) {
        return rank - o.getRank();
    }
}
