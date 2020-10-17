package com.lffblk.counter

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import java.io.*
import java.net.URL

import org.junit.Assert.*

class FileParserTest {

    private var parser = FileParser(IPParser(IPHashGenerator()))

    @Before
    fun init() {
        parser = FileParser(IPParser(IPHashGenerator()))
    }

    @Test
    fun testFileParserE2E_smallFile() {
        doTestFileParserE2E(getFileFromResources("ip-counter/ips_small.txt"), 4)
    }

    @Test
    fun testFileParserE2E_mediumFile() {
        doTestFileParserE2E(getFileFromResources("ip-counter/ips_medium.txt"), 99997)
    }

    @Test
    fun testFileParserE2E_bigFile() {
        doTestFileParserE2E(getFileFromResources("ip-counter/ips_big.txt"), 7232594)
    }

    @Test
    @Ignore("Large file (~ 120 Gb) parsing takes about an hour")
    fun testFileParserE2E_largeFile() {
        // todo: specify correct path to unzipped ip_addresses file
        val filePath = "E:\\ip_addresses\\ip_addresses"
        val file = File(filePath)
        doTestFileParserE2E(file, 1000000000)
    }

    private fun getFileFromResources(fileName: String): File {
        val resource = javaClass.classLoader.getResource(fileName)
        if (resource == null) {
            fail("There is no resource with name '$fileName'")
        }
        return File(resource!!.file)
    }

    private fun doTestFileParserE2E(file: File, expectedIPsCount: Int) {
        val uniqueIPsCount = parser.parse(file)
        assertEquals(expectedIPsCount.toLong(), uniqueIPsCount.toLong())
    }
}