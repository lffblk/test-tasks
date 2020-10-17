package com.lffblk.clone.model

import java.util.Objects
import java.util.Random

abstract class AbstractObject {
    private val yetAnotherItselfLink: AbstractObject = this
    private val randomInt: Int

    init {
        val random = Random()
        this.randomInt = random.nextInt()
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
        val `object` = other as AbstractObject?
        return this === yetAnotherItselfLink && `object`!!.randomInt == randomInt
    }

    override fun hashCode(): Int {
        return Objects.hash(randomInt)
    }
}
