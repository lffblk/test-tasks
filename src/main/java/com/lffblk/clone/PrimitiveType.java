package com.lffblk.clone;

/**
 * In case class constructor has primitive arguments, its instantiation will fail with IllegalArgumentException
 * if null values will be passed.
 *
 * This enum helps to get default primitive value by primitive (or wrapper) type, which can be used for class
 * instantiation using reflection.
 */
public enum PrimitiveType {

    BYTE("byte", (byte) 0),
    SHORT("short", (short) 0),
    INTEGER("int", 0),
    LONG("long", 0),
    FLOAT("float",  0.0f),
    DOUBLE("double", 0.0),
    CHAR("char", '0'),
    BOOLEAN("boolean", false);

    PrimitiveType(final String type, final Object defaultValue) {
        this.type = type;
        this.defaultValue = defaultValue;
    }

    private final String type;
    private final Object defaultValue;

    public Object getDefaultValue() {
        return defaultValue;
    }

    static PrimitiveType getByType(final String type) {
        for (PrimitiveType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown primitive type: %s", type));
    }
}
