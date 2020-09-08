package com.lffblk.counter;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.URL;

import static org.junit.Assert.*;

public class FileParserTest {

    private final FileParser parser = new FileParser(new IPParser(new IPHashGenerator()));

    @Test
    public void testFileParserE2E_smallFile() {
        doTestFileParserE2E(getFileFromResources("ip-counter/ips_small.txt"), 4);
    }

    @Test
    public void testFileParserE2E_mediumFile() {
        doTestFileParserE2E(getFileFromResources("ip-counter/ips_medium.txt"), 99_997);
    }

    @Test
    public void testFileParserE2E_bigFile() {
        doTestFileParserE2E(getFileFromResources("ip-counter/ips_big.txt"), 7_232_594);
    }

    @Test
    @Ignore("Large file (~ 120 Gb) parsing takes about an hour")
    public void testFileParserE2E_largeFile() {
        // todo: specify correct path to unzipped ip_addresses file
        final String filePath = "E:\\ip_addresses\\ip_addresses";
        File file = new File(filePath);
        doTestFileParserE2E(file, 1_000_000_000);
    }

    private File getFileFromResources(final String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            fail("There is no resource with name '" + fileName + "'");
        }
        return new File(resource.getFile());
    }

    private void doTestFileParserE2E(final File file, final int expectedIPsCount) {
        int uniqueIPsCount = parser.parse(file);
        assertEquals(expectedIPsCount, uniqueIPsCount);
    }
}