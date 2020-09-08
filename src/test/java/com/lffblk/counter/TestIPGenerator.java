package com.lffblk.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TestIPGenerator {

    private final Random random = new Random();

    List<String> generateIpAddressOctets() {
        return generateIpAddressOctetsInternal();
    }

    String generateIpAddress() {
        List<String> octets = generateIpAddressOctetsInternal();
        return String.join(".", octets);
    }

    private List<String> generateIpAddressOctetsInternal() {
        List<String> octets = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            octets.add(String.valueOf(random.nextInt(256)));
        }
        return octets;
    }
}
