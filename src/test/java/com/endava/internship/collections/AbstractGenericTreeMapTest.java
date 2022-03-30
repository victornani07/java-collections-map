package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public abstract class AbstractGenericTreeMapTest<K extends Comparable<K>, V> {
    private GenericTreeMap<K, V> underTest;
    private K key1;
    private K key2;
    private K key3;
    private K key4;
    private V value1;
    private V value2;
    private V value3;
    private V value4;

    protected abstract K generateKey(int k);
    protected abstract V generateValue(int k);

    @BeforeEach
    public void setUp() {
        underTest = new GenericTreeMap<>();
        key1 = this.generateKey(0);
        key2 = this.generateKey(1);
        key3 = this.generateKey(2);
        key4 = this.generateKey(3);
        value1 = this.generateValue(0);
        value2 = this.generateValue(1);
        value3 = this.generateValue(2);
        value4 = this.generateValue(3);
    }

    @Test
    @DisplayName("When adding a new key with a unique value, it should return the value added")
    public void shouldAddANewStudent() {
        V returnedValue = underTest.put(key1, value1);

        assertAll(
                () -> assertThat(returnedValue).isNull(),
                () -> assertThat(underTest.size()).isEqualTo(1),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(key1)).isTrue(),
                () -> assertThat(underTest.containsValue(value1)).isTrue()
        );
    }

    @Test
    @DisplayName("When adding the same key multiple times, it gets added only once with the last value")
    public void shouldReturnTheLastValueAdded() {
        underTest.put(key1, value1);
        underTest.put(key1, value2);
        underTest.put(key1, value3);
        underTest.put(key1, null);
        underTest.put(key1, value4);

        assertAll(
                () -> assertThat(underTest.get(key1)).isEqualTo(value4),
                () -> assertThat(underTest.containsKey(key1)).isTrue(),
                () -> assertThat(underTest.containsValue(value4)).isTrue(),
                () -> assertThat(underTest.containsValue(value1)).isFalse(),
                () -> assertThat(underTest.containsValue(value2)).isFalse(),
                () -> assertThat(underTest.containsValue(value3)).isFalse(),
                () -> assertThat(underTest.containsValue(null)).isFalse()
        );
    }

    @Test
    @DisplayName("When adding a null object as key, it should throw a NullPointerException")
    public void shouldThrowNullPointerException_whenAddingNull() {
        assertAll(
                () -> assertThatExceptionOfType(NullPointerException.class)
                        .isThrownBy(() -> underTest.put(null, generateValue(0))),
                () -> assertThat(underTest.isEmpty()).isTrue(),
                () -> assertThat(underTest.size()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("When TreeMap is empty and no-op is performed, the TreeMap should remain empty")
    public void shouldRemainEmpty() {
        assertAll(
                () -> assertThat(underTest.size()).isEqualTo(0),
                () -> assertThat(underTest.isEmpty()).isTrue()
        );
    }

    @Test
    @DisplayName("When getting a key that does not exist, it should return null")
    public void shouldReturnNull_whenStudentIsMissing() {

        assertAll(
                () -> assertThat(underTest.get(key1)).isNull(),
                () -> assertThat(underTest.containsKey(key1)).isFalse()
        );
    }

    @Test
    @DisplayName("When getting a null key, it should throw NullPointerException")
    public void shouldThrowNullPointerException_whenGettingNullKey() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.get(null));
    }

    @Test
    @DisplayName("When getting a present jey, it should return the value added with the key")
    public void shouldReturnTheValueAdded() {
        underTest.put(key1, null);

        assertAll(
                () -> assertThat(underTest.get(key1)).isNull(),
                () -> assertThat(underTest.containsKey(key1)).isTrue(),
                () -> assertThat(underTest.containsValue(null)).isTrue()
        );
    }

    @Test
    @DisplayName("When removing a null from TreeMap, it should throw a NullPointerException")
    public void shouldThrowNullPointerException_whenRemovingNull() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.remove(null));
    }

    @Test
    @DisplayName("When removing a key that is not present in TreeMap, it should return null")
    public void shouldReturnNull_whenKeyIsNotPresent() {
        assertAll(
                () -> assertThat(underTest.remove(key1)).isNull(),
                () -> assertThat(underTest.containsKey(key1)).isFalse()
        );
    }

    @Test
    @DisplayName("When removing a key that is present in TreeMap, it should return the value added with it")
    public void shouldReturnTheValue_whenRemovingAPresentStudent() {
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        underTest.put(key3, value3);

        assertAll(
                () -> assertThat(underTest.remove(key2)).isEqualTo(value2),
                () -> assertThat(underTest.remove(key3)).isEqualTo(value3),
                () -> assertThat(underTest.containsKey(key2)).isFalse(),
                () -> assertThat(underTest.containsValue(value2)).isFalse(),
                () -> assertThat(underTest.containsKey(key3)).isFalse(),
                () -> assertThat(underTest.containsValue(value3)).isFalse(),
                () -> assertThat(underTest.size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("When checking the presence of a null key, it should throw a NullPointerException")
    public void shouldThrowException_whenCheckingIfNullKeyExists() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.containsKey(null));
    }

    @Test
    @DisplayName("When checking if a present key exists, it should return true")
    public void shouldReturnTrue_whenTheKeyIsPresent() {
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        underTest.put(key3, value3);
        underTest.put(key4, value4);

        assertAll(
                () -> assertThat(underTest.containsKey(key3)).isTrue(),
                () -> assertThat(underTest.containsValue(value3)).isTrue()
        );
    }

    @Test
    @DisplayName("When checking if a present value exists, it should return true")
    public void shouldReturnTrue_ifTheValueExists() {
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        underTest.put(key3, value3);
        underTest.put(key4, value2);

        assertAll(
                () -> assertThat(underTest.containsValue(value2)).isTrue(),
                () -> assertThat(underTest.containsKey(key2)).isTrue(),
                () -> assertThat(underTest.containsKey(key4)).isTrue()
        );
    }

    @Test
    @DisplayName("When clearing a non-empty TreeMap, it should get empty")
    public void shouldGetEmpty_whenTryingToClearIt() {
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        underTest.put(key3, null);
        underTest.put(key4, value2);

        underTest.clear();

        assertAll(
                () -> assertThat( underTest.size()).isEqualTo(0),
                () -> assertThat(underTest.isEmpty()).isTrue(),
                () -> assertThat(underTest.containsKey(key1)).isFalse(),
                () -> assertThat(underTest.containsKey(key2)).isFalse(),
                () -> assertThat(underTest.containsKey(key3)).isFalse(),
                () -> assertThat(underTest.containsKey(key4)).isFalse(),
                () -> assertThat(underTest.containsValue(value1)).isFalse(),
                () -> assertThat(underTest.containsValue(null)).isFalse(),
                () -> assertThat(underTest.containsValue(value2)).isFalse()
        );
    }

    @Test
    @DisplayName("When getting a set of keys from an empty TreeMap, it should return an empty set")
    public void shouldReturnEmptySet_whenTreeMapIsEmpty() {
        Set<K> s = underTest.keySet();

        assertThat(s.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("When getting a set of keys from a non-empty TreeMap, it should return a set of all keys")
    public void shouldReturnASetOfAllKeys() {
        Set<K> expectedSet = new HashSet<>();
        underTest.put(key1, value1);
        underTest.put(key1, value2);
        underTest.put(key3, value3);
        underTest.put(key4, value4);
        underTest.put(key1, value2);
        underTest.put(key1, value1);
        expectedSet.add(key1);
        expectedSet.add(key1);
        expectedSet.add(key3);
        expectedSet.add(key4);


        Set<K> returnedSet = underTest.keySet();

        assertThat(returnedSet).isEqualTo(expectedSet);
    }

    @Test
    @DisplayName("When getting a collection of all values from an empty TreeMap, it should return an empty collection")
    public void shouldReturnEmptyCollection_whenTreeMapIsEmpty() {
        Collection<V> s = underTest.values();

        assertThat(s.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("When getting a collection of all values from a non-empty TreeMap, it should return all the values in it")
    public void shouldReturnACollectionOfAllValues() {
        Collection<V> expectedCollection = new ArrayList<>();
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        underTest.put(key3, null);
        underTest.put(key4, value2);
        expectedCollection.add(value1);
        expectedCollection.add(value2);
        expectedCollection.add(null);
        expectedCollection.add(value2);

        Collection<V> returnedCollection = underTest.values();

        assertAll(
                () -> assertThat( returnedCollection.size()).isEqualTo(expectedCollection.size()),
                () -> assertThat(expectedCollection.containsAll(returnedCollection)).isTrue(),
                () -> assertThat(returnedCollection.containsAll(expectedCollection)).isTrue()
        );
    }

    @Test
    @DisplayName("When getting a set of key-value pairs from an empty TreeMap, it should return an empty set")
    public void shouldGetEmptyPairSet_whenTreeMapIsEmpty() {
        Set<Map.Entry<K, V>> returnedSet = underTest.entrySet();

        assertThat(returnedSet.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("When getting a set of key-value pairs from a non-empty TreeMap, it should return a set of all pairs")
    public void shouldReturnASetOfAllPairs() {
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        underTest.put(key3, null);
        underTest.put(key4, value2);
        underTest.put(key2, value2);
        underTest.put(key1, value1);
        Set<Map.Entry<K, V>> expectedSet = new HashSet<>();
        expectedSet.add(new AbstractMap.SimpleEntry<>(key1, value1));
        expectedSet.add(new AbstractMap.SimpleEntry<>(key2, value2));
        expectedSet.add(new AbstractMap.SimpleEntry<>(key3, null));
        expectedSet.add(new AbstractMap.SimpleEntry<>(key4, value2));

        Set<Map.Entry<K, V>> returnedSet = underTest.entrySet();

        assertThat(returnedSet).isEqualTo(expectedSet);
    }


    @Test
    @DisplayName("When combining an existent empty TreeMap with another empty TreeMap, it should remain empty")
    public void shouldBeEmpty_whenCombiningWithEmptyTreeMap() {
        underTest.putAll(new GenericTreeMap<>());

        assertAll(
                () -> assertThat( underTest.size()).isEqualTo(0),
                () -> assertThat(underTest.isEmpty()).isTrue()
        );
    }

    @Test
    @DisplayName("When combining an existent empty TreeMap with a new non-empty TreeMap, it should have the elements added")
    public void shouldHaveTheElementsFromTheAddedTreeMap() {
        GenericTreeMap<K, V> newMap = new GenericTreeMap<>();
        newMap.put(key1, value1);
        newMap.put(key2, value2);
        newMap.put(key3, value3);
        newMap.put(key4, value4);

        underTest.putAll(newMap);

        assertAll(
                () -> assertThat(underTest.size()).isEqualTo(4),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(key1)).isTrue(),
                () -> assertThat(underTest.containsKey(key2)).isTrue(),
                () -> assertThat(underTest.containsKey(key3)).isTrue(),
                () -> assertThat(underTest.containsKey(key4)).isTrue(),
                () -> assertThat(underTest.containsValue(value1)).isTrue(),
                () -> assertThat(underTest.containsValue(value2)).isTrue(),
                () -> assertThat(underTest.containsValue(value3)).isTrue(),
                () -> assertThat(underTest.containsValue(value4)).isTrue()
        );
    }

    @Test
    @DisplayName("When combining an existent non-empty TreeMap with a new empty TreeMap, it should have the elements it had")
    public void shouldHaveTheSameElementsItHad() {
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        underTest.put(key3, value3);

        underTest.putAll(new GenericTreeMap<>());

        assertAll(
                () -> assertThat(underTest.size()).isEqualTo(3),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(key1)).isTrue(),
                () -> assertThat(underTest.containsKey(key2)).isTrue(),
                () -> assertThat(underTest.containsKey(key3)).isTrue(),
                () -> assertThat(underTest.containsValue(value1)).isTrue(),
                () -> assertThat(underTest.containsValue(value2)).isTrue(),
                () -> assertThat(underTest.containsValue(value3)).isTrue()
        );
    }

    @Test
    @DisplayName("When combining an existent non-empty TreeMaps with another non-empty TreeMap, it should have the elements of both")
    public void shouldHaveTheElementsFromBothTreeMaps() {
        underTest.put(key1, value1);
        underTest.put(key2, value2);
        GenericTreeMap<K, V> newMap = new GenericTreeMap<>();
        newMap.put(key3, value3);
        newMap.put(key4, value4);

        underTest.putAll(newMap);

        assertAll(
                () -> assertThat(underTest.size()).isEqualTo(4),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(key1)).isTrue(),
                () -> assertThat(underTest.containsKey(key2)).isTrue(),
                () -> assertThat(underTest.containsKey(key3)).isTrue(),
                () -> assertThat(underTest.containsKey(key4)).isTrue(),
                () -> assertThat(underTest.containsValue(value1)).isTrue(),
                () -> assertThat(underTest.containsValue(value2)).isTrue(),
                () -> assertThat(underTest.containsValue(value3)).isTrue(),
                () -> assertThat(underTest.containsValue(value4)).isTrue()
        );
    }
}
