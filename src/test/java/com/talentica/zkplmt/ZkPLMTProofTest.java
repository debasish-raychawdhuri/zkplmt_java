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
        int tupleLength = 2;
        int numTuples = 2;
        testZkPLMT(tupleLength, numTuples);
        testZkPLMTFail(tupleLength, numTuples);
    }

    @Test
    public void testZkPLMTFail10x10(){
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
