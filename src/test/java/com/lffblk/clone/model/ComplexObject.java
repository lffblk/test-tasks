package com.lffblk.clone.model;

import com.lffblk.clone.PrimitiveType;

import java.util.Arrays;
import java.util.Objects;

/**
 * Object with recursion links, private fields and enum
 */
public class ComplexObject extends AbstractObject implements ObjectInterface {
    private ComplexObject itself;
    private LinkedList list;
    private PrimitiveType enumValue;

    public ComplexObject(final Object[] linkedListElements,
                         final PrimitiveType enumValue) {
        itself = this;
        this.list = new LinkedList(linkedListElements);
        this.enumValue = enumValue;
    }

    public ComplexObject getItselfLink() {
        return itself;
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
        ComplexObject complexObject = (ComplexObject) o;

        return super.equals(o)
            && this == itself
            && Objects.equals(list, complexObject.list)
            && Objects.equals(enumValue, complexObject.enumValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, enumValue);
    }

    private static class LinkedList {
        private Entry head;

        LinkedList(final Object[] elements) {
            Entry entry = null;
            for (int i = elements.length - 1; i >= 0; i--) {
                entry = new Entry(elements[i], entry);
            }
            head =  entry;
        }

        Entry getFirst() {
            return head;
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
            LinkedList list = (LinkedList) o;
            return Objects.equals(head, list.head);
        }

        @Override
        public int hashCode() {
            return Objects.hash(head);
        }
    }

    private static class Entry {
        private Object data;
        private Entry next;

        Entry(final Object data, final Entry next) {
            this.data = data;
            this.next = next;
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
            Entry entry = (Entry) o;

            boolean dataEquals = data.getClass().isArray() ? Arrays.equals((Object[]) data, (Object[]) entry.data)
                : Objects.equals(data, entry.data);

            return dataEquals
                && Objects.equals(next, entry.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), data, next);
        }
    }
}
