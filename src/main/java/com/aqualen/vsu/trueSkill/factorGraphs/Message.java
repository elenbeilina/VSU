package com.aqualen.vsu.trueSkill.factorGraphs;

import java.util.Arrays;

public class Message<T> {
    private final String nameFormat;
    private final Object[] nameFormatArgs;
    public T value;

    public Message(T value, String nameFormat, Object... args) {
        this.nameFormat = nameFormat;
        nameFormatArgs = args;
        this.value = value;
    }

    @Override
    public String toString() {
        return (nameFormat == null) ? Arrays.toString(nameFormatArgs) : String.format(nameFormat, nameFormatArgs);
    }
}
