package com.aqualen.vsu.logic;

import com.aqualen.vsu.dto.RateRequest;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.Player;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.aqualen.vsu.dto.RateRequest.toPlayer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RatingLogicTest {

    @Test
    void rateUsers() {
        RateRequest rateRequest = new RateRequest(User.builder().build(), 1);

        Player player = toPlayer(rateRequest);

        assertThat(player).isNotNull();
        assertThat(player.getRank()).isEqualTo(rateRequest.getGrade());
    }
}