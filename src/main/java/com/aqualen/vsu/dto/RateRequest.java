package com.aqualen.vsu.dto;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateRequest {
    private User user;
    private int grade;

    public static Player toPlayer(RateRequest from){
        return Player.builder()
                .user(from.user)
                .rank(from.grade).build();
    }
}
