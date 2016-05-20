package main.java;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.Objects;


public class Student implements Comparable<Student> {

    private int studentId;
    private String name;
    Gender gender;
    private String surName;
    private int age;
    private LocalDate dateOfBirth;
    private int groupId;


    public Student(String name, String surName, LocalDate dateOfBirth, Gender gender, Group group) {
        studentId = IdGenerator.getUniqueId();
        this.name = name;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        groupId = group.getGroupId();
        getAge();
    }

    public Student(int studentId, String name, String surName, LocalDate dateOfBirth, Gender gender, int groupId) {
        this.studentId = studentId;
        this.name = name;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.groupId = groupId;
        getAge();
    }

    public Student(String name, String surName, LocalDate dateOfBirth, Gender gender, int groupId) {
        studentId = IdGenerator.getUniqueId();
        this.name = name;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.groupId = groupId;
        getAge();
    }

    public Student() {
    }


    public int getAge() {
        Period years = new Period(dateOfBirth, new LocalDate());
        age = years.getYears();
        return age;
    }

    public String getFullName() {
        return surName + " " + name;
    }

    @Override
    public String toString() {
        return "Student {" +
                " studentId = " + studentId +
                ", FullName = " + surName + " " + name + ", dateOfBirth = " + dateOfBirth +
                ", age = " + age + ", gender = " + gender + ", group = " + groupId + " }" + '\n';
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getStudentId() {
        return studentId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getFullName(), student.getFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName());
    }

    @Override
    public int compareTo(Student o) {
        return this.getFullName().compareTo(o.getFullName());
    }
}
