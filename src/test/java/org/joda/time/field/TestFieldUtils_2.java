package org.joda.time.field;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestFieldUtils_2 extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestFieldUtils_2.class);
    }

    public void testSafeAddInt() {
        assertEquals(0, FieldUtils.safeAdd(0, 0));

        assertEquals(9, FieldUtils.safeAdd(2, 7));
        assertEquals(-1, FieldUtils.safeAdd(2, -3));
        assertEquals(-12, FieldUtils.safeAdd(-2, -10));

        assertEquals(Integer.MAX_VALUE - 2, FieldUtils.safeAdd(Integer.MAX_VALUE, -2));
        assertEquals(Integer.MIN_VALUE + 10, FieldUtils.safeAdd(Integer.MIN_VALUE, 10));

        try {
            FieldUtils.safeAdd(Integer.MAX_VALUE, 10);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Integer.MAX_VALUE, Integer.MAX_VALUE);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Integer.MIN_VALUE, -10);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Integer.MIN_VALUE, -150);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Integer.MIN_VALUE, Integer.MIN_VALUE);
            fail();
        } catch (ArithmeticException e) {
        }
    }

    public void testSafeAddLong() {

        assertEquals(15L, FieldUtils.safeAdd(12L, 3L));
        assertEquals(-25L, FieldUtils.safeAdd(-22L, -3L));

        assertEquals(Long.MAX_VALUE - 10, FieldUtils.safeAdd(Long.MAX_VALUE, -10L));

        assertEquals(-1, FieldUtils.safeAdd(Long.MAX_VALUE, Long.MIN_VALUE));

        try {
            FieldUtils.safeAdd(Long.MAX_VALUE, 10L);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Long.MAX_VALUE, 120L);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Long.MAX_VALUE, Long.MAX_VALUE);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Long.MIN_VALUE, -10L);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Long.MIN_VALUE, -120L);
            fail();
        } catch (ArithmeticException e) {
        }

        try {
            FieldUtils.safeAdd(Long.MIN_VALUE, Long.MIN_VALUE);
            fail();
        } catch (ArithmeticException e) {
        }
    }
}
