package com.lffblk.clone;

import java.lang.reflect.*;
import java.util.*;

/**
 * Provides ability for deep copying instances of classes with any structure and size.
 */
class CopyUtils {

    /**
     * Performs deep copy of provided object.
     *
     * Following functionality is supported:
     *  1. Copying of arrays, collections and maps: as a source objects and as an inner fields;
     *  2. Copying of objects with circular references;
     *  3. Copying of fields with any access modifier;
     *  4. Copying of objects with class inheritance;
     *  5. Copying of objects by interface link;
     *  6. Strings will not be copied and will be reused as Strings are immutable;
     *  7. Enums will not be copied and will be reused as it is impossible to create additional enum instance.
     *
     * @param source object to copy
     * @return deep copy of provided object
     */
    static <T> T deepCopy(final T source) {
        return new DeepCopier().deepCopy(source);
    }

    private static class DeepCopier {

        /**
         * Used to save original objects and already instantiated copies to support copying of objects
         * with circular references.
         */
        private final Map<Object, Object> originalObjectToCopy = new HashMap<>();

        <T> T deepCopy(final T source) {
            try {
                return deepCopyInternal(source);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

        <T> T deepCopyInternal(final T source) throws IllegalAccessException {
            if (source == null) {
                return null;
            }
            if (source instanceof String) {
                // as strings are immutable, just return source object
                return source;
            }
            Class<?> sourceClass = source.getClass();
            Object copy = instantiate(source.getClass(), source);
            originalObjectToCopy.put(source, copy);
            List<Field> declaredFields = getAllDeclaredFields(sourceClass);
            for (Field field : declaredFields) {
                if (Modifier.isStatic(field.getModifiers())) continue;
                field.setAccessible(true);
                Class<?> type = field.getType();
                Object originalFieldValue = field.get(source);
                Object copiedFieldValue;
                if (type.isPrimitive()) {
                    copiedFieldValue = originalFieldValue;
                } else if (isObjectAlreadyCopied(originalFieldValue)) {
                    copiedFieldValue = originalObjectToCopy.get(originalFieldValue);
                } else {
                    copiedFieldValue = deepCopy(originalFieldValue);
                }
                field.set(copy, copiedFieldValue);
            }
            return (T) copy;
        }

        private boolean isObjectAlreadyCopied(final Object object) {
            if (!originalObjectToCopy.containsKey(object)) {
                return false;
            }
            // HashMap$KeySet and HashSet objects with same entries are equals
            Object alreadyCopied = originalObjectToCopy.keySet().stream()
                .filter(key -> key.equals(object))
                .findFirst()
                .orElse(null);
            return alreadyCopied == object;
        }

        private List<Field> getAllDeclaredFields(final Class<?> sourceClass) {
            List<Field> fields = new ArrayList<>();
            Class<?> currentClass = sourceClass;
            while (currentClass != null) {
                fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
                currentClass = currentClass.getSuperclass();
            }
            return fields;
        }

        private <T> Object instantiate(final Class<?> sourceClass,
                                       final T source) {
            if (sourceClass.isEnum()) {
                // it is impossible to create enum instance reflectively
                return source;
            }
            if (sourceClass.getDeclaredConstructors().length == 0) { // interface, primitive, array or void
                return instantiateClassWithoutConstructor(sourceClass, source);
            } else {
                return instantiateClassWithConstructor(sourceClass, source);
            }
        }

        /**
         * Class has no constructors if <code>source</code> represents an interface, a primitive type, an array class,
         * or void.
         * In case of primitive default value will be returned.
         * In case of array new array will be instantiated and filled with copied entries of source array.
         * In other cases null will be returned.
         */
        private <T> Object instantiateClassWithoutConstructor(final Class<?> sourceClass,
                                                              final T source) {
            if (sourceClass.isPrimitive()) {
                return PrimitiveType.getByType(sourceClass.getName()).getDefaultValue();
            }
            if (sourceClass.isArray()) {
                int length = source == null ? 0 : Array.getLength(source);
                Object arrayCopy = Array.newInstance(sourceClass.getComponentType(), length);
                for (int i = 0; i < length; i++) {
                    Array.set(arrayCopy, i, deepCopy(Array.get(source, i)));
                }
                return arrayCopy;
            }
            return null;
        }

        private <T> Object instantiateClassWithConstructor(final Class<?> sourceClass,
                                                           final T source) {
            if (Modifier.isAbstract(sourceClass.getModifiers())) {
                // it is impossible to instantiate abstract class
                return null;
            }
            Constructor<?> constructor = findConstructorWithMinimumParameters(sourceClass);
            assert constructor != null;
            constructor.setAccessible(true);
            Object[] arguments = new Object[constructor.getParameterCount()];
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                if (originalObjectToCopy.containsKey(source)) {
                    arguments[i] = originalObjectToCopy.get(source);
                }
                Class<?> argumentType = constructor.getParameters()[i].getType();
                if (argumentType.equals(sourceClass)) {
                    // to avoid infinite loop in case object has parameter of same type in constructor
                    arguments[i] = null;
                } else {
                    arguments[i] = instantiate(argumentType, null);
                }
            }
            try {
                return constructor.newInstance(arguments);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        private Constructor<?> findConstructorWithMinimumParameters(final Class<?> sourceClass) {
            Constructor<?> result = null;
            for (Constructor<?> constructor : sourceClass.getDeclaredConstructors()) {
                if (result == null || constructor.getParameterCount() < result.getParameterCount()) {
                    result = constructor;
                }
            }
            return result;
        }
    }
}
