package com.talentica.zkplmt;

import cafe.cryptography.curve25519.Scalar;

import java.io.Serializable;
import java.util.Objects;

public class ScalarPair implements Serializable {
    Scalar left;
    Scalar right;

    public ScalarPair(Scalar left, Scalar right) {
        this.left = left;
        this.right = right;
    }

    public Scalar getLeft() {
        return left;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScalarPair that = (ScalarPair) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public Scalar getRight() {
        return right;
    }

}
