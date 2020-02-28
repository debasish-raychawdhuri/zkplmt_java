/*
 * Copyright  2020 Talentica Software Pvt. Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.talentica.zkplmt;

import cafe.cryptography.curve25519.RistrettoElement;
import cafe.cryptography.curve25519.Scalar;

import java.io.Serializable;

public class ZkPLMTProof implements Serializable {
    private ScalarPair[] proof;

    public ZkPLMTProof(VectorTuple[] vectors, int index, Scalar witness) {
        this.proof = new ScalarPair[vectors.length];
        Scalar rightSum = Scalar.ZERO;
        for (int i = 0; i < vectors.length; i++) {
            Scalar left = Util.randomScalar();
            Scalar right = Util.chooseScalarIfEqualBranchless(i,index, Scalar.ZERO, Util.randomScalar());
            ScalarPair scalarPair = new ScalarPair(left, right);
            proof[i]=scalarPair;
            rightSum = rightSum.add(scalarPair.getRight());

        }

        RistrettoElement[] sums = new RistrettoElement[vectors.length * vectors[0].length()];
        for (int i = 0; i < vectors.length; i++) {

            for (int j = 0; j < vectors[i].length(); j++) {
                RistrettoElement element = vectors[i].members[j].getLeftPoint()
                        .multiply(proof[i].getLeft()).add(vectors[i].members[j].getRightPoint()
                                .multiply(proof[i].getRight()));
                sums[j + vectors[i].length() * i] = element;
            }

        }
        proof[index].right = Scalar.fromBytesModOrder(Util.hashOfPoints(sums)).subtract(rightSum);
        proof[index].left = proof[index].left.subtract(witness.multiply(proof[index].right));
    }

    public boolean verify(VectorTuple[] vectors){
        Scalar rightSum = Scalar.ZERO;
        for (int i = 0; i < vectors.length; i++) {
            rightSum = rightSum.add(proof[i].getRight());

        }

        RistrettoElement[] sums = new RistrettoElement[vectors.length * vectors[0].length()];
        for (int i = 0; i < vectors.length; i++) {
            for (int j = 0; j < vectors[i].length(); j++) {
                RistrettoElement element = vectors[i].members[j].getLeftPoint()
                        .multiply(proof[i].getLeft()).add(vectors[i].members[j].getRightPoint()
                                .multiply(proof[i].getRight()));
                sums[j + vectors[i].length() * i] = element;
            }

        }
        Scalar hash =  Scalar.fromBytesModOrder(Util.hashOfPoints(sums));
        return rightSum.equals(hash);
    }
}
