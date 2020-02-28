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
