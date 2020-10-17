package com.lffblk.counter

import java.util.*

/**
 * Represents unique IP hash for particular IP address.
 *
 * As BitSet could be created only for Integer.MAX_VALUE (2_147_483_647) bits number, it is required 2 BitSets
 * to manage all possible IP addresses (4_294_967_296). The number of BitSet for particular IP address will be stored
 * in 'bitSetNumber' field.
 *
 * IP addresses diapasons:
 * BitSet #0: 0.0.0.0 - 127.255.255.255
 * BitSet #1: 128.0.0.0 - 255.255.255.255
 */
internal class IPHash(val index: Int, val bitSetNumber: Int) {
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
        val hash = other as IPHash
        return (index == hash.index
                && bitSetNumber == hash.bitSetNumber)
    }

    override fun hashCode(): Int {
        return Objects.hash(index, bitSetNumber)
    }

    override fun toString(): String {
        return "IPHash{" +
                "index=" + index +
                ", bitSetNumber=" + bitSetNumber +
                '}'
    }
}