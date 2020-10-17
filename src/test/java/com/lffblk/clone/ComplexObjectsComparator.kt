package com.lffblk.clone;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

class ComplexObjectsComparator {

    /**
     * Recursively compares objects equality and ensures that objects and all fields are not same.
     * Method supports comparing objects with circular references.
     *
     * @param source source object
     * @param copy copied object
     */
    static void assertDeepCopy(final Object source, final Object copy) {
        assertDeepCopyInternal(source, copy, new HashSet<>());
    }

    private static void assertDeepCopyInternal(final Object source,
                                               final Object copy,
                                               final Set<Object> alreadyCompared) {
        if (alreadyCompared.contains(source)) {
            return;
        }
        if (source == null && copy == null) {
            return;
        }
        assertNotNull(source);
        assertNotNull(copy);
        alreadyCompared.add(source);
        if (source.getClass().isArray()) {
            assertArraysEquals(source, copy, alreadyCompared);
        } else {
            assertEquals(source, copy);
        }
        if (source instanceof String) {
            // as strings are immutable, copied value will be the same
            return;
        }
        if (source instanceof Collection) {
            // HashSet<T> implementation contains internal HashMap<T,Object>, where values are of Object type.
            // Source Object instance will not be equals to copied instance as by default equals method compares
            // references.
            return;
        }
        Field[] declaredFields = source.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                assertDeepCopyInternal(field.get(source), field.get(copy), alreadyCompared);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static void assertArraysEquals(final Object source,
                                           final Object copy,
                                           final Set<Object> alreadyCompared) {
        assertEquals(source.getClass(), copy.getClass());
        for (int i = 0; i < Array.getLength(source); i++) {
            assertDeepCopyInternal(Array.get(source, i), Array.get(copy, i), alreadyCompared);
        }
    }
}
