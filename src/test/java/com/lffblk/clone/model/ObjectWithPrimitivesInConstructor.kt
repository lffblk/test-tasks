package com.lffblk.clone.model

import java.util.Objects

class ObjectWithPrimitivesInConstructor(private val aByte: Byte, private val aShort: Short, private val anInt: Int, private val aLong: Long,
                                        private val aFloat: Float, private val aDouble: Double, private val aChar: Char,
                                        private val aBoolean: Boolean) {

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
        val `object` = other as ObjectWithPrimitivesInConstructor?
        return (aByte == `object`!!.aByte
                && aShort == `object`.aShort
                && anInt == `object`.anInt
                && aLong == `object`.aLong
                && aFloat == `object`.aFloat
                && aDouble == `object`.aDouble
                && aChar == `object`.aChar
                && aBoolean == `object`.aBoolean)
    }

    override fun hashCode(): Int {
        return Objects.hash(aByte, aShort, anInt, aLong, aFloat, aDouble, aChar, aBoolean)
    }
}
