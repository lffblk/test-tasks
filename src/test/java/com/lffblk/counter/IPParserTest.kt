package com.lffblk.counter;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IPParserTest {

    private final TestIPGenerator ipGenerator = new TestIPGenerator();
    private final IPHashGenerator generator = mock(IPHashGenerator.class);
    private IPParser parser = new IPParser(generator);

    @Before
    public void init() {
        parser = new IPParser(generator);
    }

    @Test
    public void testIpParser() {
        final String ip1 = "someIp1";
        final String ip2 = "someIp2";
        final String ip3 = "someIp3";

        when(generator.generate(ip1)).thenReturn(new IPHash(1, 0));
        when(generator.generate(ip2)).thenReturn(new IPHash(2, 1));
        when(generator.generate(ip3)).thenReturn(new IPHash(Integer.MAX_VALUE, 0));

        assertEquals(0, parser.getUniqueIPsCount());
        doTestIpParser(ip1, 1, 1);
        doTestIpParser(ip2, 1, 2);
        // duplicateLineIterator
        doTestIpParser(ip1, 2, 2);
        // duplicate
        doTestIpParser(ip2, 2, 2);
        doTestIpParser(ip3, 1, 3);
    }

    /**
     * Compares IPParser (with real IPHashGenerator) results with unique IPs, which was counted by 'naive' algorithm.
     */
    @Test
    public void testIpParserE2E() {
        parser = new IPParser(new IPHashGenerator());
        HashSet<String> uniqueIPAddresses = new HashSet<>();
        for (int i = 0; i < 1_000_000; i++) {
            String ip = ipGenerator.generateIpAddress();
            parser.parse(ip);
            uniqueIPAddresses.add(ip);
        }
        assertEquals(uniqueIPAddresses.size(), parser.getUniqueIPsCount());
    }

    private void doTestIpParser(final String ip,
                                final int expectedIpDuplicatesCount,
                                final int expectedUniqueIPAddressCount) {
        parser.parse(ip);
        verify(generator, times(expectedIpDuplicatesCount)).generate(ip);
        assertEquals(expectedUniqueIPAddressCount, parser.getUniqueIPsCount());
    }
}