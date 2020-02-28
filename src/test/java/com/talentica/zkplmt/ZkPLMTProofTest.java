package com.talentica.zkplmt;


import cafe.cryptography.curve25519.RistrettoElement;
import cafe.cryptography.curve25519.Scalar;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ZkPLMTProofTest {
    @Test
    public void testZkPLMT2x2(){
        int tupleLength = 2;
        int numTuples = 2;
        testZkPLMT(tupleLength, numTuples);

    }

    @Test
    public void testZkPLMT10x10(){
        int tupleLength = 10;
        int numTuples = 10;
        testZkPLMT(tupleLength, numTuples);

    }

    @Test
    public void testZkPLMTFail2x2(){
        int tupleLength = 10;
        int numTuples = 10;
        testZkPLMT(tupleLength, numTuples);
        testZkPLMTFail(tupleLength, numTuples);
    }

    public void testZkPLMT(int tupleLength, int numTuples){
        VectorTuple[] tuples = new VectorTuple[numTuples];
        Scalar witness = Util.randomScalar();
        int index  = Util.randomIntInRange(numTuples);
        for(int i=0;i<numTuples;i++){
            CurveVector[] members = new CurveVector[tupleLength];
            for(int j=0; j<tupleLength;j++){
                if(i!=index ){
                    members[j] = new CurveVector(Util.randomPoint(), Util.randomPoint());
                }else{
                    RistrettoElement point = Util.randomPoint();
                    members[j] = new CurveVector(point, point.multiply(witness));
                }

            }
            tuples[i] = new VectorTuple(members);
        }
        ZkPLMTProof zkProof = new ZkPLMTProof(tuples,index, witness);

        assertEquals(zkProof.verify(tuples), true);


    }

    public void testZkPLMTFail(int tupleLength, int numTuples){
        VectorTuple[] tuples = new VectorTuple[numTuples];
        Scalar witness = Util.randomScalar();
        int index  = Util.randomIntInRange(numTuples);
        for(int i=0;i<numTuples;i++){
            CurveVector[] members = new CurveVector[tupleLength];
            for(int j=0; j<tupleLength;j++){
               members[j] = new CurveVector(Util.randomPoint(), Util.randomPoint());
            }
            tuples[i] = new VectorTuple(members);
        }
        ZkPLMTProof zkProof = new ZkPLMTProof(tuples,index, witness);

        assertEquals(zkProof.verify(tuples), false);


    }
}
