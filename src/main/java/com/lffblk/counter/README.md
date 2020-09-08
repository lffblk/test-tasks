Unique IPv4 addresses counter ([entry point](FileParser.java)).

Requires file of any size with correct IPv4 addresses: each address on new line without any other symbols.

`BitSet` is used to mark unique IP addresses. As BitSet instance could be created only for `Integer.MAX_VALUE` 
(2 147 483 647) bits number, it is required 2 BitSets to manage all possible IP addresses (4 294 967 296). 

IP addresses diapasons:
 * BitSet #0: 0.0.0.0 - 127.255.255.255
 * BitSet #1: 128.0.0.0 - 255.255.255.255

High-level algorithm:

1. Read file line by line.
2. For each line:
    * Parse IP address.
    * For each IP determine appropriate BitSet and generate unique index for particular IP diapason.
    * Set bit in appropriate BitSet by calculated index to `true`.
7. Return count of bits with `true` value from all BitSets.

[Test file](https://ecwid-vgv-storage.s3.eu-central-1.amazonaws.com/ip_addresses.zip) parsing takes about an hour 
and constant memory (~ 512 Mb), file contains 1 000 000 000 unique IP addresses.

##### Memory estimation

Each `BitSet` keeps array of longs inside. Each long in array is responsible for 64 bits. 
As `BitSet` is created for `Integer.MAX_VALUE` bits, array for 

```Integer.MAX_VALUE / 64 = 33 554 432 ``` long values will be instantiated inside the `BitSet`.

Each long requires 8 bytes. So, `BitSet` size approximately is 

```33 554 432 * 8 = 268 435 456 bytes = 256 Mb.```

There are 2 `BitSet` used in current solution for unique IP addresses calculation, so, approximately 512 Mb memory 
will be consumed to keep unique and cover all possible IPv4 addresses.