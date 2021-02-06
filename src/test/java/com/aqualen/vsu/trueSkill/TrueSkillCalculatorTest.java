package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class TrueSkillCalculatorTest {

    @Autowired
    private TrueSkillCalculator trueSkillCalculator;

    @Test
    void calculateNewRatings() {
        GameInfo gameInfo = new GameInfo();
        Player player1 = Player.builder()
                .user(User.builder().id(1L).build())
                .rank(1)
                .rating(gameInfo.getDefaultRating())
                .build();
        Player player2 = Player.builder()
                .user(User.builder().id(2L).build())
                .rank(2)
                .rating(gameInfo.getDefaultRating())
                .build();
        Player player3 = Player.builder()
                .user(User.builder().id(3L).build())
                .rank(3)
                .rating(gameInfo.getDefaultRating())
                .build();

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, Arrays.asList(player1, player2, player3));

        assertThat(result).isNotNull();
    }
}