package com.aqualen.vsu.trueSkill.layers;

import com.aqualen.vsu.exceptions.ReadableException;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.Schedule;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleLoop;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleSequence;
import com.aqualen.vsu.trueSkill.factorGraphs.schedule.ScheduleStep;
import com.aqualen.vsu.trueSkill.factorGraphs.variable.Variable;
import com.aqualen.vsu.trueSkill.GameInfo;
import com.aqualen.vsu.trueSkill.GaussianDistribution;
import com.aqualen.vsu.trueSkill.Player;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(3)
@AllArgsConstructor
// The whole purpose of this is to do a loop on the bottom
public class IteratedPlayerDifferencesInnerLayer extends
        TrueSkillFactorGraphLayer<Variable<GaussianDistribution>, Variable<GaussianDistribution>> {
    private final PlayerDifferencesComparisonLayer playerDifferencesComparisonLayer;
    private final PlayerToPlayerPerformanceDifferencesLayer
            playerToPlayerPerformanceDifferencesLayer;

    @Override
    public void buildLayer(GameInfo gameInfo, List<Player> players) {
        playerToPlayerPerformanceDifferencesLayer.setRawInputVariablesGroups(getInputVariables());
        playerToPlayerPerformanceDifferencesLayer.buildLayer(gameInfo, players);

        playerDifferencesComparisonLayer.setRawInputVariablesGroups(
                playerToPlayerPerformanceDifferencesLayer.getRawOutputVariablesGroups());
        playerDifferencesComparisonLayer.buildLayer(gameInfo, players);
    }

    @Override
    public Schedule<GaussianDistribution> createPriorSchedule() {

        Schedule<GaussianDistribution> loop = switch (getInputVariables().size()) {
            case 0, 1 -> throw new ReadableException("Invalid operation");
            case 2 -> createTwoPlayerInnerPriorLoopSchedule();
            default -> createMultiplePlayerInnerPriorLoopSchedule();
        };

        // When dealing with differences, there are always (n-1) differences, so add in the 1
        int totalPlayerDifferences = playerToPlayerPerformanceDifferencesLayer.getLocalFactors().size();

        return new ScheduleSequence<>(
                "inner schedule",
                List.of(loop,
                        new ScheduleStep<>(
                                "playerToPlayerPerformanceDifferencesFactors[0] @ 1",
                                playerToPlayerPerformanceDifferencesLayer.getLocalFactors().get(0), 1),
                        new ScheduleStep<>(
                                String.format("playerToPlayerPerformanceDifferencesLayer[playerDifferences = %s - 1] @ 2",
                                        totalPlayerDifferences),
                                playerToPlayerPerformanceDifferencesLayer.getLocalFactors().get(totalPlayerDifferences - 1), 2)));
    }

    private Schedule<GaussianDistribution> createTwoPlayerInnerPriorLoopSchedule() {
        return scheduleSequence(
                List.of(
                        new ScheduleStep<>(
                                "send player perf to perf differences",
                                playerToPlayerPerformanceDifferencesLayer.getLocalFactors().get(0),
                                0),
                        new ScheduleStep<>(
                                "send to greater than or within factor",
                                playerDifferencesComparisonLayer.getLocalFactors().get(0),
                                0)
                ),
                "loop of just two players inner sequence");
    }

    private Schedule<GaussianDistribution> createMultiplePlayerInnerPriorLoopSchedule() {
        int totalTeamDifferences = playerToPlayerPerformanceDifferencesLayer.getLocalFactors().size();

        var forwardScheduleList = new ArrayList<Schedule<GaussianDistribution>>();

        for (int i = 0; i < totalTeamDifferences - 1; i++) {
            Schedule<GaussianDistribution> currentForwardSchedulePiece =
                    scheduleSequence(List.of(
                            new ScheduleStep<>(
                                    String.format("team perf to perf diff %s", i),
                                    playerToPlayerPerformanceDifferencesLayer.getLocalFactors().get(i), 0),
                            new ScheduleStep<>(
                                    String.format("greater than or within result factor %s", i),
                                    playerDifferencesComparisonLayer.getLocalFactors().get(i), 0),
                            new ScheduleStep<>(
                                    String.format("team perf to perf diff factors [%s], 2", i),
                                    playerToPlayerPerformanceDifferencesLayer.getLocalFactors().get(i), 2)),
                            "current forward schedule piece %s", i);

            forwardScheduleList.add(currentForwardSchedulePiece);
        }

        var forwardSchedule = new ScheduleSequence<>("forward schedule", forwardScheduleList);

        var backwardScheduleList = new ArrayList<Schedule<GaussianDistribution>>();

        for (int i = 0; i < totalTeamDifferences - 1; i++) {
            var currentBackwardSchedulePiece = new ScheduleSequence<>(
                    "current backward schedule piece",
                    List.of(
                            new ScheduleStep<>(
                                    String.format("playerToPlayerPerformanceDifferencesFactors[totalPlayerDifferences - 1 - %s] @ 0",
                                            i),
                                    playerToPlayerPerformanceDifferencesLayer.getLocalFactors().get(totalTeamDifferences - 1 - i), 0),
                            new ScheduleStep<>(
                                    String.format("greaterThanOrWithinResultFactors[totalPlayerDifferences - 1 - %s] @ 0",
                                            i),
                                    playerDifferencesComparisonLayer.getLocalFactors().get(totalTeamDifferences - 1 - i), 0),
                            new ScheduleStep<>(
                                    String.format("playerToPlayerPerformanceDifferencesFactors[totalPlayerDifferences - 1 - %s] @ 1",
                                            i),
                                    playerToPlayerPerformanceDifferencesLayer.getLocalFactors().get(totalTeamDifferences - 1 - i), 1)
                    )
            );
            backwardScheduleList.add(currentBackwardSchedulePiece);
        }

        var backwardSchedule =
                new ScheduleSequence<>(
                        "backward schedule",
                        backwardScheduleList);

        var forwardBackwardScheduleToLoop =
                new ScheduleSequence<>(
                        "forward Backward Schedule To Loop",
                        List.of(forwardSchedule, backwardSchedule));

        double initialMaxDelta = 0.0001;

        return new ScheduleLoop<>(
                String.format("loop with max delta of %s", initialMaxDelta),
                forwardBackwardScheduleToLoop,
                initialMaxDelta);
    }
}