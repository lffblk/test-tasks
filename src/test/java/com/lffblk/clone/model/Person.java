package com.lffblk.clone.model;

import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private List<String> favoriteBooks;

    public Person(final String name, final int age, final List<String> favoriteBooks) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(name, person.name)
            && Objects.equals(age, person.age)
            && Objects.equals(favoriteBooks, person.favoriteBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, favoriteBooks);
    }
}