package com.lffblk.counter

import java.util.*
import java.util.stream.Collectors

open class IPHashGenerator {
    /**
     * Generates unique hash for provided IP address.
     *
     * @param ip IP address
     * @return unique hash.
     */
    open fun generate(ip: String): IPHash {
        val octets = Arrays.stream(ip.split("\\.".toRegex()).toTypedArray())
                .map { s: String -> s.toInt() }
                .collect(Collectors.toList())
        val bitSetNumber = if (octets[0] < 128) FIRST_BIT_SET_NUMBER else SECOND_BIT_SET_NUMBER
        if (bitSetNumber == 1) {
            // to avoid int range overflow, decrease last octet by 128
            octets[0] = octets[0] - 128
        }
        var hash = 0
        for (i in octets.indices) {
            hash += octets[i] * Math.pow(256.0, (octets.size - i - 1).toDouble()).toInt()
        }
        return IPHash(hash, bitSetNumber)
    }

    companion object {
        const val FIRST_BIT_SET_NUMBER = 0
        const val SECOND_BIT_SET_NUMBER = 1
    }
}