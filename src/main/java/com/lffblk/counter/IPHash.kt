package com.lffblk.counter;

import java.util.Objects;

/**
 * Represents unique IP hash for particular IP address.
 *
 * As BitSet could be created only for Integer.MAX_VALUE (2_147_483_647) bits number, it is required 2 BitSets
 * to manage all possible IP addresses (4_294_967_296). The number of BitSet for particular IP address will be stored
 * in 'bitSetNumber' field.
 *
 * IP addresses diapasons:
 * BitSet #0: 0.0.0.0 - 127.255.255.255
 * BitSet #1: 128.0.0.0 - 255.255.255.255
 */
class IPHash {

    private int index;
    private int bitSetNumber;

    IPHash(final int index, final int bitSetNumber) {
        this.index = index;
        this.bitSetNumber = bitSetNumber;
    }

    int getIndex() {
        return index;
    }

    int getBitSetNumber() {
        return bitSetNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        IPHash hash = (IPHash) o;
        return Objects.equals(index, hash.index)
            && Objects.equals(bitSetNumber, hash.bitSetNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, bitSetNumber);
    }

    @Override
    public String toString() {
        return "IPHash{" +
            "index=" + index +
            ", bitSetNumber=" + bitSetNumber +
            '}';
    }
}
