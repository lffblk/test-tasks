package com.lffblk.counter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.lffblk.counter.IPHashGenerator.*;
import static org.junit.Assert.*;

public class IPHashGeneratorTest {

    private final TestIPGenerator ipGenerator = new TestIPGenerator();

    private final IPHashGenerator generator = new IPHashGenerator();

    @Test
    public void testBitSetsSymmetry_cornerCases() {
        doTestBitSetsSymmetry("0.0.0.0", "128.0.0.0");
        doTestBitSetsSymmetry("127.255.255.255", "255.255.255.255");
    }

    @Test
    public void testBitSetsSymmetry_randomCases() {
        for (int i = 0; i < 1_000_000; i++) {
            String[] ipPair = generateIPPair();
            doTestBitSetsSymmetry(ipPair[0], ipPair[1]);
        }
    }

    @Test
    public void testUniqueIndexGenerationCornerCases() {
        assertEquals(256, generator.generate("0.0.1.0").getIndex());
        assertEquals(255, generator.generate("0.0.0.255").getIndex());

        assertEquals(65_536, generator.generate("0.1.0.0").getIndex());
        assertEquals(65_535, generator.generate("0.0.255.255").getIndex());

        assertEquals(16_777_216, generator.generate("1.0.0.0").getIndex());
        assertEquals(16_777_215, generator.generate("0.255.255.255").getIndex());
    }

    @Test
    public void testForCollisions() {
        HashMap<IPHash, String> hashToIp = new HashMap<>();
        for (int i = 0; i < 1_000_000; i++) {
            String ip = ipGenerator.generateIpAddress();
            IPHash hash = generator.generate(ip);
            if (hashToIp.containsKey(hash)) {
                String existedIp = hashToIp.get(hash);
                if (!ip.equals(existedIp)) {
                    fail(String.format("Collision found! Following IP addresses [%s, %s] have the same hash %s",
                        existedIp, ip, hash));
                }
            }
            hashToIp.put(hash, ip);
        }
    }

    /**
     * Generates pair of IP addresses from different BitSets which must have the same index.
     *
     * @return array of two Strings containing generated IP addresses.
     */
    private String[] generateIPPair() {
        List<String> octets = ipGenerator.generateIpAddressOctets();
        int firstOctet = Integer.parseInt(octets.get(0));

        List<String> octetsFromAnotherBitSet = new ArrayList<>(octets);
        int firstOctetFromAnotherBitSet = firstOctet < 128 ? firstOctet + 128 : firstOctet - 128;
        octetsFromAnotherBitSet.set(0, String.valueOf(firstOctetFromAnotherBitSet));

        String ip = String.join(".", octets);
        String ipFromAnotherBitSet = String.join(".", octetsFromAnotherBitSet);

        String[] ipPair = new String[2];
        if (firstOctet < 128) {
            ipPair[0] = ip;
            ipPair[1] = ipFromAnotherBitSet;
        } else {
            ipPair[0] = ipFromAnotherBitSet;
            ipPair[1] = ip;
        }
        return ipPair;
    }

    private void doTestBitSetsSymmetry(final String ipFromFirstBitSet,
                                       final String ipFromSecondBitSet) {
        IPHash firstHash = generator.generate(ipFromFirstBitSet);
        IPHash secondHash = generator.generate(ipFromSecondBitSet);
        assertEquals(FIRST_BIT_SET_NUMBER, firstHash.getBitSetNumber());
        assertEquals(SECOND_BIT_SET_NUMBER, secondHash.getBitSetNumber());
        assertEquals(firstHash.getIndex(), secondHash.getIndex());
    }
}