package com.lffblk.counter

import org.junit.Test

import java.util.ArrayList
import java.util.HashMap

import com.lffblk.counter.IPHashGenerator.*
import org.junit.Assert.*

class IPHashGeneratorTest {

    private val ipGenerator = TestIPGenerator()

    private val generator = IPHashGenerator()

    @Test
    fun testBitSetsSymmetry_cornerCases() {
        doTestBitSetsSymmetry("0.0.0.0", "128.0.0.0")
        doTestBitSetsSymmetry("127.255.255.255", "255.255.255.255")
    }

    @Test
    fun testBitSetsSymmetry_randomCases() {
        for (i in 0..999999) {
            val ipPair = generateIPPair()
            doTestBitSetsSymmetry(ipPair[0], ipPair[1])
        }
    }

    @Test
    fun testUniqueIndexGenerationCornerCases() {
        assertEquals(256, generator.generate("0.0.1.0").index.toLong())
        assertEquals(255, generator.generate("0.0.0.255").index.toLong())

        assertEquals(65536, generator.generate("0.1.0.0").index.toLong())
        assertEquals(65535, generator.generate("0.0.255.255").index.toLong())

        assertEquals(16777216, generator.generate("1.0.0.0").index.toLong())
        assertEquals(16777215, generator.generate("0.255.255.255").index.toLong())
    }

    @Test
    fun testForCollisions() {
        val hashToIp = HashMap<IPHash, String>()
        for (i in 0..999999) {
            val ip = ipGenerator.generateIpAddress()
            val hash = generator.generate(ip)
            if (hashToIp.containsKey(hash)) {
                val existedIp = hashToIp[hash]
                if (ip != existedIp) {
                    fail(String.format("Collision found! Following IP addresses [%s, %s] have the same hash %s",
                            existedIp, ip, hash))
                }
            }
            hashToIp[hash] = ip
        }
    }

    /**
     * Generates pair of IP addresses from different BitSets which must have the same index.
     *
     * @return array of two Strings containing generated IP addresses.
     */
    private fun generateIPPair(): Array<String?> {
        val octets = ipGenerator.generateIpAddressOctets()
        val firstOctet = Integer.parseInt(octets[0])

        val octetsFromAnotherBitSet = ArrayList(octets)
        val firstOctetFromAnotherBitSet = if (firstOctet < 128) firstOctet + 128 else firstOctet - 128
        octetsFromAnotherBitSet[0] = firstOctetFromAnotherBitSet.toString()

        val ip = octets.joinToString(".")
        val ipFromAnotherBitSet = octetsFromAnotherBitSet.joinToString(".")

        val ipPair = arrayOfNulls<String>(2)
        if (firstOctet < 128) {
            ipPair[0] = ip
            ipPair[1] = ipFromAnotherBitSet
        } else {
            ipPair[0] = ipFromAnotherBitSet
            ipPair[1] = ip
        }
        return ipPair
    }

    private fun doTestBitSetsSymmetry(ipFromFirstBitSet: String?,
                                      ipFromSecondBitSet: String?) {
        val firstHash = generator.generate(ipFromFirstBitSet)
        val secondHash = generator.generate(ipFromSecondBitSet)
        assertEquals(FIRST_BIT_SET_NUMBER.toLong(), firstHash.bitSetNumber.toLong())
        assertEquals(SECOND_BIT_SET_NUMBER.toLong(), secondHash.bitSetNumber.toLong())
        assertEquals(firstHash.index.toLong(), secondHash.index.toLong())
    }
}