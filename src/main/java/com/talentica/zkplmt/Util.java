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
import cafe.cryptography.curve25519.Scalar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Util {
    private static SecureRandom random = new SecureRandom();
    public static Scalar randomScalar(){
        byte [] buf = new byte[32];
        random.nextBytes(buf);
        return Scalar.fromBytesModOrder(buf);
    }

    public static ScalarPair randomScalarPair(){
        return new ScalarPair(randomScalar(), randomScalar());
    }

    public static RistrettoElement randomPoint(){
        byte [] buf = new byte[64];
        random.nextBytes(buf);
        return RistrettoElement.fromUniformBytes(buf);
    }

    public static int randomIntInRange(int mod){
        return Math.abs(random.nextInt()) % mod;
    }

    public static Scalar chooseScalarIfEqualBranchless(int lhs, int rhs, Scalar ifEq, Scalar ifNEq){
        int diff = lhs - rhs;
        int bi = diff | (-diff); //must be negative if not equal, zero otherwise.
        byte bd = (byte)(bi >>> 63);
        byte [] buf = new byte[32];
        for(int i=31;i>0;i--){
            buf[i]=0;
        }
        buf[0] = bd;
        Scalar b = Scalar.fromBytesModOrder(buf);
        return b.multiplyAndAdd(ifNEq, Scalar.ONE.subtract(b).multiply(ifEq));
    }

    public static byte[] hashOfPoints(RistrettoElement[] points){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            ByteArrayOutputStream bout = new ByteArrayOutputStream(32*points.length);
            ObjectOutputStream out = new ObjectOutputStream(bout);
            for(RistrettoElement point:points){
                out.writeObject(point);
            }
            return md.digest(bout.toByteArray());
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
