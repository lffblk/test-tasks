package com.lffblk.counter

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

internal class FileParser(private val ipParser: IPParser) {

    /**
     * Parses IP addresses from file with provided name line by line and returns total unique
     * IP addresses count.
     *
     * @param file file containing IP addresses: each line should contain IP address.
     * @return unique IP addresses count.
     */
    fun parse(file: File): Int {
        try {
            BufferedReader(FileReader(file)).use { reader ->
                var line: String?
                do {
                    line = reader.readLine()
                    if (line != null) ipParser.parse(line)
                } while(line != null)
            }
        } catch (e: Exception) {
            throw IllegalStateException(e)
        }

        return ipParser.uniqueIPsCount
    }
}
