package com.aqualen.vsu.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArgumentUtils {
    public void argumentNotNull(Object value, String parameterName) {
        if (value == null) {
            throw new NullPointerException(parameterName);
        }
    }

    public void argumentIsValidIndex(int index, int count, String parameterName) {
        if ((index < 0) || (index >= count)) {
            throw new ArrayIndexOutOfBoundsException(parameterName);
        }
    }
}