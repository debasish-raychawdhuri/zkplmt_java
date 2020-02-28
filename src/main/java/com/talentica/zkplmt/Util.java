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
        long lhsl = lhs;
        long rhsl = rhs;
        long diff = lhsl - rhsl;
        long prod = diff * (-diff); //must be negative if not equal, positive otherwise.
        byte bd = (byte)(prod >>> 63);
        byte [] buf = new byte[32];
        for(int i=31;i>0;i--){
            buf[i]=0;
        }
        buf[0] = bd;
        Scalar b = Scalar.fromBytesModOrder(buf);
        return b.multiply(ifNEq).add( Scalar.ONE.subtract(b).multiply(ifEq));
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
