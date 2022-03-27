package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class StudentMapTest {

    private StudentMap underTest;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;

    @BeforeEach
    public void setUp() {
        underTest = new StudentMap();
        student1 = new Student("Victor Nani", LocalDate.parse("1999-10-07"), "Internship at Endava");
        student2 = new Student("Harry Potter", LocalDate.parse("2000-01-01"), "Studying magic");
        student3 = new Student("Alice", LocalDate.parse("1299-10-07"), "Wandering in Wonderland");
        student4 = new Student("Elon Musk", LocalDate.parse("1333-11-11"), "Top of the world");
    }

    @Test
    @DisplayName("When adding a Student with a unique value, it should return the value added")
    public void shouldAddANewStudent() {
        Integer returnedValue = underTest.put(student1, 22);

        assertAll(
                () -> assertThat(returnedValue).isNull(),
                () -> assertThat(underTest.size()).isEqualTo(1),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(student1)).isTrue(),
                () -> assertThat(underTest.containsValue(22)).isTrue()
        );
    }

    @Test
    @DisplayName("When adding the same Student multiple times, it gets added only once with the last value")
    public void shouldReturnTheLastValueAdded() {
        underTest.put(student1, 404);
        underTest.put(student1, 405);
        underTest.put(student1, 406);
        underTest.put(student1, null);
        underTest.put(student1, 408);

        assertAll(
                () -> assertThat(underTest.get(student1)).isEqualTo(408),
                () -> assertThat(underTest.containsKey(student1)).isTrue(),
                () -> assertThat(underTest.containsValue(408)).isTrue(),
                () -> assertThat(underTest.containsValue(404)).isFalse(),
                () -> assertThat(underTest.containsValue(405)).isFalse(),
                () -> assertThat(underTest.containsValue(406)).isFalse(),
                () -> assertThat(underTest.containsValue(null)).isFalse()
        );
    }

    @Test
    @DisplayName("When adding a null object as key, it should throw a NullPointerException")
    public void shouldThrowNullPointerException_whenAddingNull() {
        assertAll(
                () -> assertThatExceptionOfType(NullPointerException.class)
                        .isThrownBy(() -> underTest.put(null, 1)),
                () -> assertThat(underTest.isEmpty()).isTrue(),
                () -> assertThat(underTest.size()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("When TreeMap is empty and no-op is performed, the TreeMap should remain empty")
    public void shouldRemainEmpty() {
        assertAll(
                () -> assertEquals(0, underTest.size()),
                () -> assertTrue(underTest.isEmpty())
        );
    }

    @Test
    @DisplayName("When getting a Student that does not exist, it should return null")
    public void shouldReturnNull_whenStudentIsMissing() {
        assertAll(
                () -> assertThat(underTest.get(student1)).isNull(),
                () -> assertThat(underTest.containsKey(student1)).isFalse()
        );
    }

    @Test
    @DisplayName("When getting a null key, it should throw NullPointerException")
    public void shouldThrowNullPointerException_whenGettingNullKey() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.get(null));
    }

    @Test
    @DisplayName("When getting a present Student, it should return the value added with the Student")
    public void shouldReturnTheValueAdded() {
        underTest.put(student1, null);

        assertAll(
                () -> assertThat(underTest.get(student1)).isNull(),
                () -> assertThat(underTest.containsKey(student1)).isTrue(),
                () -> assertThat(underTest.containsValue(null)).isTrue()
        );
    }

    @Test
    @DisplayName("When getting an object that is not a reference of Student class, it should return null")
    public void shouldReturnNullIfNotStudent() {
        assertThat(underTest.get("Aurel")).isNull();
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
                () -> assertThat(underTest.remove(student1)).isNull(),
                () -> assertThat(underTest.containsKey(student1)).isFalse()
        );
    }

    @Test
    @DisplayName("When removing a Student that is present in TreeMap, it should return the value added with it")
    public void shouldReturnTheValue_whenRemovingAPresentStudent() {
        underTest.put(student1, 1);
        underTest.put(student2, 22);
        underTest.put(student3, 333);

        assertAll(
                () -> assertThat(underTest.remove(student2)).isEqualTo(22),
                () -> assertThat(underTest.remove(student3)).isEqualTo(333),
                () -> assertThat(underTest.containsKey(student2)).isFalse(),
                () -> assertThat(underTest.containsValue(22)).isFalse(),
                () -> assertThat(underTest.containsKey(student3)).isFalse(),
                () -> assertThat(underTest.containsValue(333)).isFalse(),
                () -> assertThat(underTest.size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("When removing an object that is not a reference of Student class, it should return null")
    public void shouldReturnNull_ifNotRemovingAStudent() {
        assertThat(underTest.remove("Aurel")).isNull();
    }

    @Test
    @DisplayName("When checking the presence of a null key, it should throw a NullPointerException")
    public void shouldThrowException_whenCheckingIfNullKeyExists() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> underTest.containsKey(null));
    }

    @Test
    @DisplayName("When checking the presence of an object that is not a reference of Student class, it should return false")
    public void shouldReturnFalse_ifKeyIsNotStudent() {
        assertThat(underTest.containsKey(10)).isFalse();
    }

    @Test
    @DisplayName("When checking if a present Student exists, it should return true")
    public void shouldReturnTrue_whenTheKeyIsPresent() {
        underTest.put(student1, 14);
        underTest.put(student2, 49);
        underTest.put(student3, 56);
        underTest.put(student4, 49);

        assertAll(
                () -> assertThat(underTest.containsKey(student3)).isTrue(),
                () -> assertThat(underTest.containsValue(56)).isTrue()
        );
    }

    @Test
    @DisplayName("When checking if a present value exists, it should return true")
    public void shouldReturnTrue_ifTheValueExists() {
        underTest.put(student1, 14);
        underTest.put(student2, 49);
        underTest.put(student3, 56);
        underTest.put(student4, 49);

        assertAll(
                () -> assertThat(underTest.containsValue(49)).isTrue(),
                () -> assertThat(underTest.containsKey(student2)).isTrue(),
                () -> assertThat(underTest.containsKey(student4)).isTrue()
        );
    }

    @Test
    @DisplayName("When clearing a non-empty TreeMap, it should get empty")
    public void shouldGetEmpty_whenTryingToClearIt() {
        underTest.put(student1, 14);
        underTest.put(student2, 49);
        underTest.put(student3, null);
        underTest.put(student4, 49);

        underTest.clear();

        assertAll(
                () -> assertThat( underTest.size()).isEqualTo(0),
                () -> assertThat(underTest.isEmpty()).isTrue(),
                () -> assertThat(underTest.containsKey(student1)).isFalse(),
                () -> assertThat(underTest.containsKey(student2)).isFalse(),
                () -> assertThat(underTest.containsKey(student3)).isFalse(),
                () -> assertThat(underTest.containsKey(student4)).isFalse(),
                () -> assertThat(underTest.containsValue(14)).isFalse(),
                () -> assertThat(underTest.containsValue(null)).isFalse(),
                () -> assertThat(underTest.containsValue(49)).isFalse()
        );
    }

    @Test
    @DisplayName("When getting a set of keys from an empty TreeMap, it should return an empty set")
    public void shouldReturnEmptySet_whenTreeMapIsEmpty() {
        Set<Student> s = underTest.keySet();

        assertThat(s.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("When getting a set of keys from a non-empty TreeMap, it should return a set of all keys")
    public void shouldReturnASetOfAllKeys() {
        Set<Student> expectedSet = new HashSet<>();
        underTest.put(student1, 1);
        underTest.put(student2, 2);
        underTest.put(student3, 4);
        underTest.put(student4, 6);
        underTest.put(student2, 2);
        underTest.put(student1, 1);
        expectedSet.add(student1);
        expectedSet.add(student2);
        expectedSet.add(student3);
        expectedSet.add(student4);


        Set<Student> returnedSet = underTest.keySet();

        assertThat(returnedSet).isEqualTo(expectedSet);
    }

    @Test
    @DisplayName("When getting a collection of all values from an empty TreeMap, it should return an empty collection")
    public void shouldReturnEmptyCollection_whenTreeMapIsEmpty() {
        Collection<Integer> s = underTest.values();

        assertThat(s.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("When getting a collection of all values from a non-empty TreeMap, it should return all the values in it")
    public void shouldReturnACollectionOfAllValues() {
        Collection<Integer> expectedCollection = new ArrayList<>();
        underTest.put(student1, 1);
        underTest.put(student2, 2);
        underTest.put(student3, null);
        underTest.put(student4, 2);
        expectedCollection.add(1);
        expectedCollection.add(2);
        expectedCollection.add(null);
        expectedCollection.add(2);

        Collection<Integer> returnedCollection = underTest.values();

        assertAll(
                () -> assertThat( returnedCollection.size()).isEqualTo(expectedCollection.size()),
                () -> assertThat(expectedCollection.containsAll(returnedCollection)).isTrue(),
                () -> assertThat(returnedCollection.containsAll(expectedCollection)).isTrue()
        );
    }

    @Test
    @DisplayName("When getting a set of key-value pairs from an empty TreeMap, it should return an empty set")
    public void shouldGetEmptyPairSet_whenTreeMapIsEmpty() {
        Set<Map.Entry<Student, Integer>> returnedSet = underTest.entrySet();

        assertThat(returnedSet.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("When getting a set of key-value pairs from a non-empty TreeMap, it should return a set of all pairs")
    public void shouldReturnASetOfAllPairs() {
        underTest.put(student1, 1);
        underTest.put(student2, 2);
        underTest.put(student3, null);
        underTest.put(student4, 2);
        underTest.put(student2, 2);
        underTest.put(student1, 1);
        Set<Map.Entry<Student, Integer>> expectedSet = new HashSet<>();
        expectedSet.add(new AbstractMap.SimpleEntry<>(student1, 1));
        expectedSet.add(new AbstractMap.SimpleEntry<>(student2, 2));
        expectedSet.add(new AbstractMap.SimpleEntry<>(student3, null));
        expectedSet.add(new AbstractMap.SimpleEntry<>(student4, 2));

        Set<Map.Entry<Student, Integer>> returnedSet = underTest.entrySet();

        assertThat(returnedSet).isEqualTo(expectedSet);
    }


    @Test
    @DisplayName("When combining an existent empty TreeMap with another empty TreeMap, it should remain empty")
    public void shouldBeEmpty_whenCombiningWithEmptyTreeMap() {
        underTest.putAll(new StudentMap());

        assertAll(
                () -> assertThat( underTest.size()).isEqualTo(0),
                () -> assertThat(underTest.isEmpty()).isTrue()
        );
    }

    @Test
    @DisplayName("When combining an existent empty TreeMap with a new non-empty TreeMap, it should have the elements added")
    public void shouldHaveTheElementsFromTheAddedTreeMap() {
        StudentMap newMap = new StudentMap();
        newMap.put(student1, 1);
        newMap.put(student2, 2);
        newMap.put(student3, 3);
        newMap.put(student4, 4);

        underTest.putAll(newMap);

        assertAll(
                () -> assertThat(underTest.size()).isEqualTo(4),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(student1)).isTrue(),
                () -> assertThat(underTest.containsKey(student2)).isTrue(),
                () -> assertThat(underTest.containsKey(student3)).isTrue(),
                () -> assertThat(underTest.containsKey(student4)).isTrue(),
                () -> assertThat(underTest.containsValue(1)).isTrue(),
                () -> assertThat(underTest.containsValue(2)).isTrue(),
                () -> assertThat(underTest.containsValue(3)).isTrue(),
                () -> assertThat(underTest.containsValue(4)).isTrue()
        );
    }

    @Test
    @DisplayName("When combining an existent non-empty TreeMap with a new empty TreeMap, it should have the elements it had")
    public void shouldHaveTheSameElementsItHad() {
        underTest.put(student1, 4);
        underTest.put(student2, 3);
        underTest.put(student3, 5);

        underTest.putAll(new StudentMap());

        assertAll(
                () -> assertThat(underTest.size()).isEqualTo(3),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(student1)).isTrue(),
                () -> assertThat(underTest.containsKey(student2)).isTrue(),
                () -> assertThat(underTest.containsKey(student3)).isTrue(),
                () -> assertThat(underTest.containsValue(4)).isTrue(),
                () -> assertThat(underTest.containsValue(3)).isTrue(),
                () -> assertThat(underTest.containsValue(5)).isTrue()
        );
    }

    @Test
    @DisplayName("When combining an existent non-empty TreeMaps with another non-empty TreeMap, it should have the elements of both")
    public void shouldHaveTheElementsFromBothTreeMaps() {
        underTest.put(student1, 4);
        underTest.put(student2, 3);
        StudentMap newMap = new StudentMap();
        newMap.put(student3, 6);
        newMap.put(student4, 13);

        underTest.putAll(newMap);

        assertAll(
                () -> assertThat(underTest.size()).isEqualTo(4),
                () -> assertThat(underTest.isEmpty()).isFalse(),
                () -> assertThat(underTest.containsKey(student1)).isTrue(),
                () -> assertThat(underTest.containsKey(student2)).isTrue(),
                () -> assertThat(underTest.containsKey(student3)).isTrue(),
                () -> assertThat(underTest.containsKey(student4)).isTrue(),
                () -> assertThat(underTest.containsValue(4)).isTrue(),
                () -> assertThat(underTest.containsValue(3)).isTrue(),
                () -> assertThat(underTest.containsValue(6)).isTrue(),
                () -> assertThat(underTest.containsValue(13)).isTrue()
        );
    }

    @ParameterizedTest
    @MethodSource("provideStudentsAndSetForEntrySetTesting")
    public void testGettingASetOfAllPairs(List<Student> students, Set<Map.Entry<Student, Integer>> expectedSet) {
        underTest.put(students.get(0), 1);
        underTest.put(students.get(1), 2);
        underTest.put(students.get(2), null);
        underTest.put(students.get(3), 2);
        underTest.put(students.get(1), 2);
        underTest.put(students.get(0), 1);
        expectedSet.add(new AbstractMap.SimpleEntry<>(students.get(0), 1));
        expectedSet.add(new AbstractMap.SimpleEntry<>(students.get(1), 2));
        expectedSet.add(new AbstractMap.SimpleEntry<>(students.get(2), null));
        expectedSet.add(new AbstractMap.SimpleEntry<>(students.get(3), 2));

        Set<Map.Entry<Student, Integer>> returnedSet = underTest.entrySet();

        assertThat(expectedSet).isEqualTo(returnedSet);
    }

    /**
     * We could make a few lists of students and pass them to Arguments.of()
     * And Stream.of() would make a stream of these combination of arguments
     * and feed them to the test method which is using this particular method.
     * In this way, we stimulate the refactoring and the same test could be run
     * with different kind of parameters and all this was achieved with minimum
     * effort.
     */
    private static Stream<Arguments> provideStudentsAndSetForEntrySetTesting() {
        List<Student> students = Arrays.asList(
                new Student("AAAA", LocalDate.parse("2020-01-01"), "AAAA-AAAA"),
                new Student("BBBB", LocalDate.parse("2021-03-04"), "BBBB-BBBB"),
                new Student("CCCC", LocalDate.parse("2019-05-15"), "CCCC-CCCC"),
                new Student("DDDD", LocalDate.parse("2022-02-03"), "DDDD-DDDD")
        );
        Set<Map.Entry<Student, Integer>> expectedSet = new HashSet<>();

        return Stream.of(
                Arguments.of(students, expectedSet)
        );
    }
}