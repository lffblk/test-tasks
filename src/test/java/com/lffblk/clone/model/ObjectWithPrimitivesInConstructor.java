package com.lffblk.clone.model;

import java.util.Objects;

public class ObjectWithPrimitivesInConstructor {

    private byte aByte;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private char aChar;
    private boolean aBoolean;

    public ObjectWithPrimitivesInConstructor(final byte aByte, final short aShort, final int anInt, final long aLong,
                                             final float aFloat, final double aDouble, final char aChar,
                                             final boolean aBoolean) {
        this.aByte = aByte;
        this.aShort = aShort;
        this.anInt = anInt;
        this.aLong = aLong;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
        this.aChar = aChar;
        this.aBoolean = aBoolean;
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
        ObjectWithPrimitivesInConstructor object = (ObjectWithPrimitivesInConstructor) o;
        return aByte == object.aByte
            && aShort == object.aShort
            && anInt == object.anInt
            && aLong == object.aLong
            && aFloat == object.aFloat
            && aDouble == object.aDouble
            && aChar == object.aChar
            && aBoolean == object.aBoolean;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aByte, aShort, anInt, aLong, aFloat, aDouble, aChar, aBoolean);
    }
}
