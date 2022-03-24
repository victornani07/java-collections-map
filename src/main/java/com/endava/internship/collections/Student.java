package com.endava.internship.collections;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The class that defines the element that will be contained by your collection
 */
public class Student implements Comparable<Student> {
    private final String name;
    private final LocalDate dateOfBirth;
    private final String details;

    public Student(String name, LocalDate dateOfBirth, String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public String getName() { return name; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public String getDetails() { return details; }

    @Override
    public int compareTo(Student o) {
        int namesComparator = name.compareTo(o.name);
        int datesOfBirthComparator = dateOfBirth.compareTo(o.dateOfBirth);

        if(name.compareTo(o.name) != 0) {
            return namesComparator;
        }

        if(datesOfBirthComparator != 0) {
            return datesOfBirthComparator;
        }

        return details.compareTo(o.details);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", details='" + details + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(dateOfBirth, student.dateOfBirth) && Objects.equals(details, student.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth, details);
    }
}
