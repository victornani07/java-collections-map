package com.endava.internship.collections;

public class CharacterDoubleTreeMapTest extends
        AbstractGenericTreeMapTest<Character, Double> {
    @Override
    protected Character generateKey(int k) {
        Character[] characters = new Character[]{'A', 'B', 'C', 'D'};

        return characters[k];
    }

    @Override
    protected Double generateValue(int k) {
        Double[] doubles = new Double[]{2.22, 42.42, 567.567, 123.321};

        return doubles[k];
    }
}
