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
            ScalarPair scalarPair = Util.randomScalarPair();
            proof[i] = scalarPair;
            if (i==index){
                proof[i].right = Scalar.ZERO;
            }
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
