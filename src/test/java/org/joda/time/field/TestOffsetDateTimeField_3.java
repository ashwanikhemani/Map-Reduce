package org.joda.time.field;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.ISOChronology;

public class TestOffsetDateTimeField_3 extends TestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestOffsetDateTimeField_3.class);
    }
    public TestOffsetDateTimeField_3(String name) {
        super(name);
    }

    public void test_constructor1() {
        OffsetDateTimeField field = new OffsetDateTimeField(
                ISOChronology.getInstance().dayOfYear(), 3
        );
        assertEquals(DateTimeFieldType.dayOfYear(), field.getType());
        assertEquals(3, field.getOffset());

        try {
            field = new OffsetDateTimeField(null, 3);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            field = new OffsetDateTimeField(ISOChronology.getInstance().dayOfYear(), 0);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            field = new OffsetDateTimeField(UnsupportedDateTimeField.getInstance(
                    DateTimeFieldType.secondOfMinute(), UnsupportedDurationField.getInstance(DurationFieldType.seconds())), 0);
            fail();
        } catch (IllegalArgumentException ex) {}
    }

    public void test_constructor2() {
        OffsetDateTimeField field = new OffsetDateTimeField(
                ISOChronology.getInstance().dayOfYear(), DateTimeFieldType.monthOfYear(), 3
        );
        assertEquals(DateTimeFieldType.monthOfYear(), field.getType());
        assertEquals(3, field.getOffset());

        try {
            field = new OffsetDateTimeField(null, DateTimeFieldType.secondOfDay(), 3);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            field = new OffsetDateTimeField(ISOChronology.getInstance().dayOfYear(), null, 3);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            field = new OffsetDateTimeField(ISOChronology.getInstance().dayOfYear(), DateTimeFieldType.monthOfYear(), 0);
            fail();
        } catch (IllegalArgumentException ex) {}
    }

}
