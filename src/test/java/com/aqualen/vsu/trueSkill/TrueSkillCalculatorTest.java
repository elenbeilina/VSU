package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
class TrueSkillCalculatorTest {

    @Autowired
    private TrueSkillCalculator trueSkillCalculator;
    private final static double ErrorTolerance = 0.085;

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

        Rating player1NewRating = result.get(0).getRating();
        assertRating(31.675352419172107, 6.6559853776206905, player1NewRating);

        Rating player2NewRating = result.get(1).getRating();
        assertRating(25.000000000003912, 6.2078966412243233, player2NewRating);

        Rating player3NewRating = result.get(2).getRating();
        assertRating(18.324647580823971, 6.6559853776218318, player3NewRating);
    }

    private static void assertRating(double expectedMean, double expectedStandardDeviation, Rating actual) {
        assertEquals(actual.getMean(), expectedMean, ErrorTolerance);
        assertEquals(actual.getStandardDeviation(), expectedStandardDeviation, ErrorTolerance);
    }
}