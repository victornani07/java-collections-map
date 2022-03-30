package com.endava.internship.collections;

public class StringIntegerTreeMapTest extends
        AbstractGenericTreeMapTest<String, Integer> {
    @Override
    protected String generateKey(int k) {
        String[] strings = new String[]{"Victor", "Viorel", "Aurel", "Madalina"};

        return strings[k];
    }

    @Override
    protected Integer generateValue(int k) {
        Integer[] integers = new Integer[]{2, 42, 567, 123};

        return integers[k];
    }
}
