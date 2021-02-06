package com.aqualen.vsu.trueSkill.FactorGraphs.variable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
public class KeyedVariable<TKey, TValue> extends Variable<TValue> {
    private TKey Key;

    public KeyedVariable(TKey key, String name, TValue prior) {
        super(name, prior);
        Key = key;
    }
}