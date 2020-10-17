package com.lffblk.counter

import java.util.ArrayList
import java.util.Random

internal class TestIPGenerator {

    private val random = Random()

    fun generateIpAddressOctets(): List<String> {
        return generateIpAddressOctetsInternal()
    }

    fun generateIpAddress(): String {
        val octets = generateIpAddressOctetsInternal()
        return octets.joinToString(".")
    }

    private fun generateIpAddressOctetsInternal(): List<String> {
        val octets = ArrayList<String>(4)
        for (i in 0..3) {
            octets.add(random.nextInt(256).toString())
        }
        return octets
    }
}
