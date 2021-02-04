package com.aqualen.vsu.trueSkill.FactorGraphs;

import java.util.Arrays;

public class Message<T>
{
    private String _NameFormat;
    private Object[] _NameFormatArgs;
    public T Value;

    public Message(T value, String nameFormat, Object... args) {
        _NameFormat = nameFormat;
        _NameFormatArgs = args;
        Value = value;
    }

    @Override
    public String toString() {
        return (_NameFormat == null) ? Arrays.toString(_NameFormatArgs) : String.format(_NameFormat, _NameFormatArgs);
    }
}
