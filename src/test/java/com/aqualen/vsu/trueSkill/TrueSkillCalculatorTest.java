package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TrueSkillCalculatorTest {

    @Autowired
    private TrueSkillCalculator trueSkillCalculator;
    private final static double ErrorTolerance = 0.085;

    @Test
    void calculateNewRatingsThreePlayers() {
        GameInfo gameInfo = new GameInfo();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            players.add(Player.builder()
                    .user(User.builder().id(i).build())
                    .rank(i)
                    .rating(gameInfo.getDefaultRating())
                    .build());
        }

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, players);

        assertThat(result).isNotNull();

        Rating player1NewRating = result.get(0).getRating();
        assertRating(31.675352419172107, 6.6559853776206905, player1NewRating);
        Rating player2NewRating = result.get(1).getRating();
        assertRating(25.000000000003912, 6.2078966412243233, player2NewRating);
        Rating player3NewRating = result.get(2).getRating();
        assertRating(18.324647580823971, 6.6559853776218318, player3NewRating);
    }

    @Test
    void calculateNewRatingsFourPlayers() {
        GameInfo gameInfo = new GameInfo();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            players.add(Player.builder()
                    .user(User.builder().id(i).build())
                    .rank(i)
                    .rating(gameInfo.getDefaultRating())
                    .build());
        }

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, players);

        assertThat(result).isNotNull();

        Rating player1NewRating = result.get(0).getRating();
        assertRating(33.206680965631264, 6.3481091698077057, player1NewRating);
        Rating player2NewRating = result.get(1).getRating();
        assertRating(27.401454693843323, 5.7871629348447584, player2NewRating);
        Rating player3NewRating = result.get(2).getRating();
        assertRating(22.598545306188374, 5.7871629348413451, player3NewRating);
        Rating player4NewRating = result.get(3).getRating();
        assertRating(16.793319034361271, 6.3481091698144967, player4NewRating);
    }

    @Test
    void calculateNewRatingsFivePlayers() {
        GameInfo gameInfo = new GameInfo();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            players.add(Player.builder()
                    .user(User.builder().id(i).build())
                    .rank(i)
                    .rating(gameInfo.getDefaultRating())
                    .build());
        }

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, players);

        assertThat(result).isNotNull();

        Rating player1NewRating = result.get(0).getRating();
        assertRating(34.363135705841188, 6.1361528798112692, player1NewRating);
        Rating player2NewRating = result.get(1).getRating();
        assertRating(29.058448805636779, 5.5358352402833413, player2NewRating);
        Rating player3NewRating = result.get(2).getRating();
        assertRating(25.000000000031758, 5.4200805474429847, player3NewRating);
        Rating player4NewRating = result.get(3).getRating();
        assertRating(20.941551194426314, 5.5358352402709672, player4NewRating);
        Rating player5NewRating = result.get(4).getRating();
        assertRating(15.636864294158848, 6.136152879829349, player5NewRating);
    }

    private static void assertRating(double expectedMean, double expectedStandardDeviation, Rating actual) {
        assertEquals(actual.getMean(), expectedMean, ErrorTolerance);
        assertEquals(actual.getStandardDeviation(), expectedStandardDeviation, ErrorTolerance);
    }
}