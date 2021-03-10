package com.aqualen.vsu.trueSkill.factorGraphs.variable;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class KeyedVariable<TKey, TValue> extends Variable<TValue> {
    private TKey key;

    public KeyedVariable(TKey key, String name, TValue prior) {
        super(prior, name);
        this.key = key;
    }
}