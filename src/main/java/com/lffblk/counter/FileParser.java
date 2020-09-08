package com.lffblk.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class FileParser {

    private final IPParser ipParser;

    FileParser(final IPParser ipParser) {
        this.ipParser = ipParser;
    }

    /**
     * Parses IP addresses from file with provided name line by line and returns total unique
     * IP addresses count.
     *
     * @param file file containing IP addresses: each line should contain IP address.
     * @return unique IP addresses count.
     */
    int parse(final File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                ipParser.parse(line);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return ipParser.getUniqueIPsCount();
    }
}
