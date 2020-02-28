/*
 * Copyright  2020 Talentica Software Pvt. Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

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
