package com.aqualen.vsu.logic;

import com.aqualen.vsu.dto.ParticipantResponse;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.trueSkill.Player;
import org.junit.jupiter.api.Test;

import static com.aqualen.vsu.dto.ParticipantResponse.toPlayer;
import static org.assertj.core.api.Assertions.assertThat;

class RatingLogicTest {

    @Test
    void rateUsers() {
        ParticipantResponse rateRequest = new ParticipantResponse(User.builder().build(), 1, "1");

        Player player = toPlayer(rateRequest);

        assertThat(player).isNotNull();
        assertThat(player.getRank()).isEqualTo(rateRequest.getGrade());
    }
}