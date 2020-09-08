Unique IPv4 addresses counter ([entry point](FileParser.java)).

Requires file of any size with correct IPv4 addresses: each address on new line without any other symbols.

`BitSet` is used to mark unique IP addresses. As BitSet instance could be created only for `Integer.MAX_VALUE` 
(2_147_483_647) bits number, it is required 2 BitSets to manage all possible IP addresses (4_294_967_296). 

IP addresses diapasons:
 * BitSet #0: 0.0.0.0 - 127.255.255.255
 * BitSet #1: 128.0.0.0 - 255.255.255.255

High-level algorithm:

1. Read file line by line.
2. For each line:
    3. Parse IP address.
    5. For each IP determine appropriate BitSet and generate unique index for particular IP diapason.
    6. Set bit in appropriate BitSet by calculated index to `true`.
7. Return count of bits with `true` value from all BitSets.