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
