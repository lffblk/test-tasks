package com.lffblk.clone.model

import com.lffblk.clone.PrimitiveType

import java.util.Arrays
import java.util.Objects

/**
 * Object with recursion links, private fields and enum
 */
class ComplexObject(linkedListElements: Array<Any>,
                    private val enumValue: PrimitiveType?) : AbstractObject(), ObjectInterface {
    private val itself: ComplexObject = this
    private val list: LinkedList

    init {
        this.list = LinkedList(linkedListElements)
    }

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
        val complexObject = other as ComplexObject?

        return (super.equals(other)
                && this === itself
                && list == complexObject!!.list
                && enumValue == complexObject.enumValue)
    }

    override fun hashCode(): Int {
        return Objects.hash(list, enumValue)
    }

    private class LinkedList internal constructor(elements: Array<Any>) {
        internal val first: Entry?

        init {
            var entry: Entry? = null
            for (i in elements.indices.reversed()) {
                entry = Entry(elements[i], entry)
            }
            first = entry
        }

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
            val list = other as LinkedList?
            return first == list!!.first
        }

        override fun hashCode(): Int {
            return Objects.hash(first)
        }
    }

    private class Entry internal constructor(private val data: Any, private val next: Entry?) {

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
            val entry = other as Entry?

            val dataEquals = if (data.javaClass.isArray)
                Arrays.equals(data as Array<Any>, entry!!.data as Array<Any>)
            else
                data == entry!!.data

            return dataEquals && next == entry.next
        }

        override fun hashCode(): Int {
            return Objects.hash(super.hashCode(), data, next)
        }
    }
}
