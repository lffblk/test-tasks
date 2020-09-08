package com.lffblk.counter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class IPHashGenerator {

    static final int FIRST_BIT_SET_NUMBER = 0;
    static final int SECOND_BIT_SET_NUMBER = 1;

    /**
     * Generates unique hash for provided IP address.
     *
     * @param ip IP address
     * @return unique hash.
     */
    IPHash generate(final String ip) {
        List<Integer> octets = Arrays.stream(ip.split("\\."))
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        int bitSetNumber = octets.get(0) < 128 ? FIRST_BIT_SET_NUMBER : SECOND_BIT_SET_NUMBER;

        if (bitSetNumber == 1) {
            // to avoid int range overflow, decrease last octet by 128
            octets.set(0, octets.get(0) - 128);
        }

        int hash = 0;
        for (int i = 0; i < octets.size(); i++) {
            hash += octets.get(i) * (int) Math.pow(256, (octets.size() - i - 1));
        }

        return new IPHash(hash, bitSetNumber);
    }
}
