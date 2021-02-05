package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import lombok.Value;

@Value
public class Player {
    User user;
    Rating rating;
    int rank;
}
