package com.talentica.zkplmt;

import cafe.cryptography.curve25519.RistrettoElement;

import java.io.Serializable;
import java.util.Objects;

public class CurveVector implements Serializable {
    private RistrettoElement leftPoint;
    private RistrettoElement rightPoint;

    public CurveVector(RistrettoElement leftPoint, RistrettoElement rightPoint) {
        if(leftPoint == null || rightPoint == null){
            throw new IllegalArgumentException("Null curve points are not allowed");
        }
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;
    }

    public RistrettoElement getLeftPoint() {
        return leftPoint;
    }

    public RistrettoElement getRightPoint() {
        return rightPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurveVector that = (CurveVector) o;
        return leftPoint.equals(that.leftPoint) &&
                rightPoint.equals(that.rightPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftPoint, rightPoint);
    }
}
