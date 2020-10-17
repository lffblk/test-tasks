package com.lffblk.clone

import java.lang.reflect.Array
import java.lang.reflect.Field
import java.util.HashSet

import org.junit.Assert.*

internal object ComplexObjectsComparator {

    /**
     * Recursively compares objects equality and ensures that objects and all fields are not same.
     * Method supports comparing objects with circular references.
     *
     * @param source source object
     * @param copy copied object
     */
    fun assertDeepCopy(source: Any, copy: Any) {
        assertDeepCopyInternal(source, copy, HashSet())
    }

    private fun assertDeepCopyInternal(source: Any?,
                                       copy: Any?,
                                       alreadyCompared: MutableSet<Any>) {
        if (alreadyCompared.contains(source)) {
            return
        }
        if (source == null && copy == null) {
            return
        }
        assertNotNull(source)
        assertNotNull(copy)
        alreadyCompared.add(source!!)
        if (source.javaClass.isArray) {
            assertArraysEquals(source, copy!!, alreadyCompared)
        } else {
            assertEquals(source, copy)
        }
        if (source is String) {
            // as strings are immutable, copied value will be the same
            return
        }
        if (source is Collection<*>) {
            // HashSet<T> implementation contains internal HashMap<T,Object>, where values are of Object type.
            // Source Object instance will not be equals to copied instance as by default equals method compares
            // references.
            return
        }
        val declaredFields = source.javaClass.declaredFields
        for (field in declaredFields) {
            field.isAccessible = true
            try {
                assertDeepCopyInternal(field.get(source), field.get(copy), alreadyCompared)
            } catch (e: IllegalAccessException) {
                throw IllegalStateException(e)
            }

        }
    }

    private fun assertArraysEquals(source: Any,
                                   copy: Any,
                                   alreadyCompared: MutableSet<Any>) {
        assertEquals(source.javaClass, copy.javaClass)
        for (i in 0 until Array.getLength(source)) {
            assertDeepCopyInternal(Array.get(source, i), Array.get(copy, i), alreadyCompared)
        }
    }
}
