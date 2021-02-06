package com.aqualen.vsu.trueSkill.FactorGraphs.variable;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class KeyedVariable<TKey, TValue> extends Variable<TValue> {
    private TKey key;

    public KeyedVariable(TKey key, String name, TValue prior) {
        super(name, prior);
        this.key = key;
    }
}