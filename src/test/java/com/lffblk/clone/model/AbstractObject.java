package com.lffblk.clone.model;

import java.util.Objects;
import java.util.Random;

abstract class AbstractObject {
    private AbstractObject yetAnotherItselfLink;
    private final int randomInt;
    private static int randomStaticField;

    AbstractObject() {
        this.yetAnotherItselfLink = this;
        Random random = new Random();
        this.randomInt = random.nextInt();
        randomStaticField = random.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        AbstractObject object = (AbstractObject) o;
        return this == yetAnotherItselfLink
            && object.randomInt == randomInt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(randomInt, randomStaticField);
    }
}
