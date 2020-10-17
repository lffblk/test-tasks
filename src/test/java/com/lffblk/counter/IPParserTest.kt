package com.lffblk.counter

import org.junit.Before
import org.junit.Test

import java.util.HashSet

import org.junit.Assert.*
import org.mockito.Mockito.*

class IPParserTest {

    private val ipGenerator = TestIPGenerator()
    private val generator = mock(IPHashGenerator::class.java)
    private var parser = IPParser(generator)

    @Before
    fun init() {
        parser = IPParser(generator)
    }

    @Test
    fun testIpParser() {
        val ip1 = "someIp1"
        val ip2 = "someIp2"
        val ip3 = "someIp3"

        `when`(generator.generate(ip1)).thenReturn(IPHash(1, 0))
        `when`(generator.generate(ip2)).thenReturn(IPHash(2, 1))
        `when`(generator.generate(ip3)).thenReturn(IPHash(Integer.MAX_VALUE, 0))

        assertEquals(0, parser.uniqueIPsCount.toLong())
        doTestIpParser(ip1, 1, 1)
        doTestIpParser(ip2, 1, 2)
        // duplicateLineIterator
        doTestIpParser(ip1, 2, 2)
        // duplicate
        doTestIpParser(ip2, 2, 2)
        doTestIpParser(ip3, 1, 3)
    }

    /**
     * Compares IPParser (with real IPHashGenerator) results with unique IPs, which was counted by 'naive' algorithm.
     */
    @Test
    fun testIpParserE2E() {
        parser = IPParser(IPHashGenerator())
        val uniqueIPAddresses = HashSet<String>()
        for (i in 0..999999) {
            val ip = ipGenerator.generateIpAddress()
            parser.parse(ip)
            uniqueIPAddresses.add(ip)
        }
        assertEquals(uniqueIPAddresses.size.toLong(), parser.uniqueIPsCount.toLong())
    }

    private fun doTestIpParser(ip: String,
                               expectedIpDuplicatesCount: Int,
                               expectedUniqueIPAddressCount: Int) {
        parser.parse(ip)
        verify(generator, times(expectedIpDuplicatesCount)).generate(ip)
        assertEquals(expectedUniqueIPAddressCount.toLong(), parser.uniqueIPsCount.toLong())
    }
}