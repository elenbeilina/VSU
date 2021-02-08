package com.aqualen.vsu.trueSkill;

public class Guard {
    public static void argumentNotNull(Object value, String parameterName) {
        if (value == null) {
            throw new NullPointerException(parameterName);
        }
    }

    public static void argumentIsValidIndex(int index, int count, String parameterName) {
        if ((index < 0) || (index >= count)) {
            throw new ArrayIndexOutOfBoundsException(parameterName);
        }
    }

    public static void argumentInRangeInclusive(double value, double min, double max, String parameterName) {
        if ((value < min) || (value > max)) {
            throw new ArrayIndexOutOfBoundsException(parameterName);
        }
    }
}