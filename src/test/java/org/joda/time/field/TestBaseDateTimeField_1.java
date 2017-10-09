package org.joda.time.field;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.TimeOfDay;

import java.util.Arrays;

public class TestBaseDateTimeField_1 extends TestCase {
    /**
     * Asserts that two objects are equal. If they are not
     * an AssertionFailedError is thrown.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestBaseDateTimeField_1.class);
    }


    @SuppressWarnings("deprecation")
    public static void assertEquals(Object expected, Object actual) {
        Assert.assertEquals(expected, actual);
    }

    /**
     * Fails a test with no message.
     */
    @SuppressWarnings("deprecation")


    public static void fail() {
        Assert.fail();
    }

    public void test_constructor() {
        BaseDateTimeField field = new MockBaseDateTimeField();
        assertEquals(DateTimeFieldType.hourOfDay(), field.getType());
        try {
            field = new MockBaseDateTimeField(null);
            fail();
        } catch (IllegalArgumentException ex) {}
    }

    public void test_getType() {
        BaseDateTimeField field = new MockBaseDateTimeField(DateTimeFieldType.hourOfDay());
        assertEquals(DateTimeFieldType.hourOfDay(), field.getType());
    }

    public void test_getName() {
        BaseDateTimeField field = new MockBaseDateTimeField(DateTimeFieldType.hourOfDay());
        assertEquals("hourOfDay", field.getName());
    }

    static class MockBaseDateTimeField extends BaseDateTimeField {
        protected MockBaseDateTimeField() {
            super(DateTimeFieldType.hourOfDay());
        }
        protected MockBaseDateTimeField(DateTimeFieldType type) {
            super(type);
        }
        public int get(long instant) {
            return (int) (instant / 60L);
        }
        public long set(long instant, int value) {
            return 1000 + value;
        }
        public DurationField getDurationField() {
            return new TestBaseDateTimeField_1.MockCountingDurationField(DurationFieldType.seconds());
        }
        public DurationField getRangeDurationField() {
            return new TestBaseDateTimeField_1.MockCountingDurationField(DurationFieldType.minutes());
        }
        public int getMinimumValue() {
            return 0;
        }
        public int getMaximumValue() {
            return 59;
        }
        public long roundFloor(long instant) {
            return (instant / 60L) * 60L;
        }
        public boolean isLenient() {
            return false;
        }
    }

    public void test_addWrapField_RP_int_intarray_int() {
        BaseDateTimeField field = new TestBaseDateTimeField_1.MockBaseDateTimeField();
        int[] values = new int[] {11, 22, 33, 44};
        int[] expected = new int[] {13, 14, 48, 16};
        int[] result = field.addWrapField(new TimeOfDay(), 2, values, 1);
        assertEquals(true, Arrays.equals(result, expected));


        values = new int[] {14, 20, 30, 34};
        expected = new int[] {15, 20, 1, 23};
        result = field.addWrapField(new TimeOfDay(), 1, values, 35);
        assertEquals(true, Arrays.equals(result, expected));
    }
    static class MockCountingDurationField extends BaseDurationField {
        static int add_int = 0;
        static int add_long = 0;
        static int difference_long = 0;

        protected MockCountingDurationField(DurationFieldType type) {
            super(type);
        }

        public boolean isPrecise() {
            return false;
        }

        public long getUnitMillis() {
            return 0;
        }

        public long getValueAsLong(long duration, long instant) {
            return 0;
        }

        public long getMillis(int value, long instant) {
            return 0;
        }

        public long getMillis(long value, long instant) {
            return 0;
        }

        public long add(long instant, int value) {
            add_int++;
            return instant + (value * 60L);
        }

        public long add(long instant, long value) {
            add_long++;
            return instant + (value * 60L);
        }

        public long getDifferenceAsLong(long minuendInstant, long subtrahendInstant) {
            difference_long++;
            return 30;
        }
    }

        public void test_roundHalfFloor_long() {
            BaseDateTimeField field = new MockBaseDateTimeField();
            assertEquals(0L, field.roundHalfFloor(1L));
            assertEquals(0L, field.roundHalfFloor(9L));
            assertEquals(60L, field.roundHalfFloor(35L));
            assertEquals(60L, field.roundHalfFloor(36L));
            assertEquals(60L, field.roundHalfFloor(56L));
        }

        public void test_roundHalfCeiling_long() {
            BaseDateTimeField field = new MockBaseDateTimeField();
            assertEquals(0L, field.roundHalfCeiling(1L));
            assertEquals(0L, field.roundHalfCeiling(9L));
            assertEquals(60L, field.roundHalfCeiling(35L));
            assertEquals(60L, field.roundHalfCeiling(36L));
            assertEquals(60L, field.roundHalfCeiling(56L));
        }
    }

