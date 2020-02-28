package com.talentica.zkplmt;


import cafe.cryptography.curve25519.Scalar;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import java.security.SecureRandom;

public class UtilTest {
    @Test
    public void testChooseScalarIfEqualBranchLessEqual(){
        SecureRandom random = new SecureRandom();
        int lhs = random.nextInt();
        Scalar ifEq = Util.randomScalar();
        Scalar ifNeq = Util.randomScalar();
        Scalar output = Util.chooseScalarIfEqualBranchless(lhs,lhs,ifEq, ifNeq);
        assertEquals(ifEq, output);
    }

    @Test
    public void testChooseScalarIfEqualBranchLessNotEqual(){
        SecureRandom random = new SecureRandom();
        int lhs = random.nextInt();
        int rhs = random.nextInt();
        Scalar ifEq = Util.randomScalar();
        Scalar ifNeq = Util.randomScalar();
        Scalar output = Util.chooseScalarIfEqualBranchless(lhs,rhs,ifEq, ifNeq);
        assertEquals(ifNeq, output);
    }

    @Test
    public void testChooseScalarIfEqualBranchLessNotEqual2(){
        SecureRandom random = new SecureRandom();
        int lhs = random.nextInt();
        Scalar ifEq = Util.randomScalar();
        Scalar ifNeq = Util.randomScalar();
        Scalar output = Util.chooseScalarIfEqualBranchless(lhs,lhs+1,ifEq, ifNeq);
        assertEquals(ifNeq, output);
    }

    @Test
    public void testChooseScalarIfEqualBranchLessNotEqual3(){
        SecureRandom random = new SecureRandom();
        int lhs = random.nextInt();
        Scalar ifEq = Util.randomScalar();
        Scalar ifNeq = Util.randomScalar();
        Scalar output = Util.chooseScalarIfEqualBranchless(0, 1,ifEq, ifNeq);
        assertEquals(ifNeq, output);
    }
}
