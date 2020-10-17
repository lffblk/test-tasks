package com.lffblk.counter

import java.util.*

internal class IPParser(private val hashGenerator: IPHashGenerator) {
    private val ipBitSets = arrayOf(BitSet(Int.MAX_VALUE), BitSet(Int.MAX_VALUE))

    /**
     * For received IP address calculates its hash which consists of BitSet number and unique for IP diapason
     * (BitSet #0: 0.0.0.0 - 127.255.255.255, BitSet #1: 128.0.0.0 - 255.255.255.255) index. After that sets
     * the bit in appropriate BitSet at the specified index to `true`.
     *
     * @param ip IP address to be processed.
     */
    fun parse(ip: String?) {
        val hash = hashGenerator.generate(ip!!)
        ipBitSets[hash.bitSetNumber].set(hash.index)
    }

    /**
     * Calculates and retrieves parsed unique IP addresses count.
     *
     * @return unique IP addresses count.
     */
    val uniqueIPsCount: Int
        get() = ipBitSets[0].cardinality() + ipBitSets[1].cardinality()
}