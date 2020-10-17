package com.lffblk.clone.model

import java.util.Objects

class Person(val name: String, private val age: Int, private val favoriteBooks: List<String>?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val person = other as Person?
        return (name == person!!.name
                && age == person.age
                && favoriteBooks == person.favoriteBooks)
    }

    override fun hashCode(): Int {
        return Objects.hash(name, age, favoriteBooks)
    }
}