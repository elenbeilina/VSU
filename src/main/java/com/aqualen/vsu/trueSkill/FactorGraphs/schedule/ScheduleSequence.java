package com.aqualen.vsu.trueSkill.FactorGraphs.schedule;

import java.util.List;

public class ScheduleSequence<TValue> extends ScheduleKeySequence<TValue, Schedule<TValue>> {
    public ScheduleSequence(String name, List<Schedule<TValue>> schedules) {
        super(name, schedules);
    }
}
