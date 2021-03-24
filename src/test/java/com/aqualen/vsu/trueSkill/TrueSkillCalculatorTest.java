package com.aqualen.vsu.trueSkill;

import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.enums.TechnologyName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Collections;
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
    void threePlayersWithOneTechnologiesPlayersTest() {
        GameInfo gameInfo = new GameInfo();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            players.add(Player.builder()
                    .user(User.builder().id(i).build())
                    .rank(i)
                    .skills(Collections.singletonList(Technology.builder().language(TechnologyName.JAVA).rating(gameInfo.getDefaultRating()).build()))
                    .build());
        }

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, players);

        assertThat(result).isNotNull();

        Rating player1NewRating = result.get(0).getSkills().get(0).getRating();
        assertRating(31.675352419172107, 6.6559853776206905, player1NewRating);
        Rating player2NewRating = result.get(1).getSkills().get(0).getRating();
        assertRating(25.000000000003912, 6.2078966412243233, player2NewRating);
        Rating player3NewRating = result.get(2).getSkills().get(0).getRating();
        assertRating(18.324647580823971, 6.6559853776218318, player3NewRating);
    }

    @Test
    void fourPlayersWithOneTechnologiesPlayersTest() {
        GameInfo gameInfo = new GameInfo();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            players.add(Player.builder()
                    .user(User.builder().id(i).build())
                    .rank(i)
                    .skills(Collections.singletonList(Technology.builder().language(TechnologyName.JAVA).rating(gameInfo.getDefaultRating()).build()))
                    .build());
        }

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, players);

        assertThat(result).isNotNull();

        Rating player1NewRating = result.get(0).getSkills().get(0).getRating();
        assertRating(33.206680965631264, 6.3481091698077057, player1NewRating);
        Rating player2NewRating = result.get(1).getSkills().get(0).getRating();
        assertRating(27.401454693843323, 5.7871629348447584, player2NewRating);
        Rating player3NewRating = result.get(2).getSkills().get(0).getRating();
        assertRating(22.598545306188374, 5.7871629348413451, player3NewRating);
        Rating player4NewRating = result.get(3).getSkills().get(0).getRating();
        assertRating(16.793319034361271, 6.3481091698144967, player4NewRating);
    }

    @Test
    void fivePlayersWithOneTechnologiesPlayersTest() {
        GameInfo gameInfo = new GameInfo();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            players.add(Player.builder()
                    .user(User.builder().id(i).build())
                    .rank(i)
                    .skills(Collections.singletonList(Technology.builder().language(TechnologyName.JAVA).rating(gameInfo.getDefaultRating()).build()))
                    .build());
        }

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, players);

        assertThat(result).isNotNull();

        Rating player1NewRating = result.get(0).getSkills().get(0).getRating();
        assertRating(34.363135705841188, 6.1361528798112692, player1NewRating);
        Rating player2NewRating = result.get(1).getSkills().get(0).getRating();
        assertRating(29.058448805636779, 5.5358352402833413, player2NewRating);
        Rating player3NewRating = result.get(2).getSkills().get(0).getRating();
        assertRating(25.000000000031758, 5.4200805474429847, player3NewRating);
        Rating player4NewRating = result.get(3).getSkills().get(0).getRating();
        assertRating(20.941551194426314, 5.5358352402709672, player4NewRating);
        Rating player5NewRating = result.get(4).getSkills().get(0).getRating();
        assertRating(15.636864294158848, 6.136152879829349, player5NewRating);
    }

    @Test
    void twoPlayersWithTwoTechnologiesTest() {
        Player player1 = Player.builder()
                .user(User.builder().id(1).build())
                .rank(1)
                .skills(List.of(Technology.builder().language(TechnologyName.JAVA).rating(new Rating(20, 8)).build(),
                        Technology.builder().language(TechnologyName.PYTHON).rating(new Rating(25, 6)).build()))
                .build();

        Player player2 = Player.builder()
                .user(User.builder().id(2).build())
                .rank(2)
                .skills(List.of(Technology.builder().language(TechnologyName.JAVA).rating(new Rating(35, 7)).build(),
                        Technology.builder().language(TechnologyName.PYTHON).rating(new Rating(40, 5)).build()))
                .build();

        GameInfo gameInfo = new GameInfo();

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, new ArrayList<>() {{
            add(player1);
            add(player2);
        }});

        assertThat(result).isNotNull();

        Rating javaRatingPlayer1 = result.get(0).getSkills().get(0).getRating();
        Rating pythonRatingPlayer1 = result.get(0).getSkills().get(1).getRating();

        assertRating(29.698, 7.008, javaRatingPlayer1);
        assertRating(30.455, 5.594, pythonRatingPlayer1);
        assertThat(javaRatingPlayer1.getConservativeRating()).isEqualTo(9);
        assertThat(pythonRatingPlayer1.getConservativeRating()).isEqualTo(14);

        // Loser
        Rating javaRatingPlayer2 = result.get(1).getSkills().get(0).getRating();
        Rating pythonRatingPlayer2 = result.get(1).getSkills().get(1).getRating();

        assertRating(27.575, 6.346, javaRatingPlayer2);
        assertRating(36.211, 4.768, pythonRatingPlayer2);
        assertThat(javaRatingPlayer2.getConservativeRating()).isEqualTo(9);
        assertThat(pythonRatingPlayer2.getConservativeRating()).isEqualTo(22);
    }

    @Test
    void twoPlayersWithTwoTechnologiesPartialPlayTest() {
        GameInfo gameInfo = new GameInfo();

        Player player1 = Player.builder()
                .user(User.builder().id(1).build())
                .rank(1)
                .skills(List.of(Technology.builder().language(TechnologyName.JAVA).rating(new Rating(25, 7)).percent(70).build(),
                        Technology.builder().language(TechnologyName.PYTHON).rating(new Rating(20, 6)).percent(30).build()))
                .build();

        Player player2 = Player.builder()
                .user(User.builder().id(2).build())
                .rank(2)
                .skills(List.of(Technology.builder().language(TechnologyName.JAVA).rating(new Rating(35, 7)).percent(70).build(),
                        Technology.builder().language(TechnologyName.PYTHON).rating(new Rating(40, 5)).percent(30).build()))
                .build();

        List<Player> result = trueSkillCalculator.calculateNewRatings(gameInfo, new ArrayList<>() {{
            add(player1);
            add(player2);
        }});

        // Winner
        Rating javaRatingPlayer1 = result.get(0).getSkills().get(0).getRating();
        Rating pythonRatingPlayer1 = result.get(0).getSkills().get(1).getRating();

        assertRating(33.09, 5.94, javaRatingPlayer1);
        assertRating(22.54, 5.88, pythonRatingPlayer1);
        assertThat(javaRatingPlayer1.getConservativeRating()).isEqualTo(15);
        assertThat(pythonRatingPlayer1.getConservativeRating()).isEqualTo(5);

        // Loser
        Rating javaRatingPlayer2 = result.get(1).getSkills().get(0).getRating();
        Rating pythonRatingPlayer2 = result.get(1).getSkills().get(1).getRating();

        assertRating(26.901, 5.94, javaRatingPlayer2);
        assertRating(38.229, 4.934, pythonRatingPlayer2);
        assertThat(javaRatingPlayer2.getConservativeRating()).isEqualTo(9);
        assertThat(pythonRatingPlayer2.getConservativeRating()).isEqualTo(23);
    }

    private static void assertRating(double expectedMean, double expectedStandardDeviation, Rating actual) {
        assertEquals(actual.getMean(), expectedMean, ErrorTolerance);
        assertEquals(actual.getStandardDeviation(), expectedStandardDeviation, ErrorTolerance);
    }
}