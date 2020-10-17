package com.lffblk.counter;

import java.util.BitSet;

class IPParser {

    private final IPHashGenerator hashGenerator;
    private final BitSet[] ipBitSets = {new BitSet(Integer.MAX_VALUE), new BitSet(Integer.MAX_VALUE)};

    IPParser(final IPHashGenerator hashGenerator) {
        this.hashGenerator = hashGenerator;
    }

    /**
     * For received IP address calculates its hash which consists of BitSet number and unique for IP diapason
     * (BitSet #0: 0.0.0.0 - 127.255.255.255, BitSet #1: 128.0.0.0 - 255.255.255.255) index. After that sets
     * the bit in appropriate BitSet at the specified index to {@code true}.
     *
     * @param ip IP address to be processed.
     */
    void parse(final String ip) {
        IPHash hash = hashGenerator.generate(ip);
        ipBitSets[hash.getBitSetNumber()].set(hash.getIndex());
    }

    /**
     * Calculates and retrieves parsed unique IP addresses count.
     *
     * @return unique IP addresses count.
     */
    int getUniqueIPsCount() {
        return ipBitSets[0].cardinality() + ipBitSets[1].cardinality();
    }
}
