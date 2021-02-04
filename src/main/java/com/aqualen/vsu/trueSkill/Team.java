package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import lombok.Getter;

import java.util.Map;

@Getter
public class Team {
    Map<User,Rating> players;
}
