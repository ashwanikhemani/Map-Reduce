package org.joda.time.field;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.ISOChronology;

public class TestOffsetDateTimeField_2 extends TestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static TestSuite suite() {
        return new TestSuite(TestOffsetDateTimeField_2.class);
    }

    public TestOffsetDateTimeField_2(String name) {
        super(name);
    }

    public void test_constructor1() {
        OffsetDateTimeField field = new OffsetDateTimeField(
                ISOChronology.getInstance().dayOfMonth(), 4
        );
        assertEquals(DateTimeFieldType.dayOfMonth(), field.getType());
        assertEquals(4, field.getOffset());

        try {
            field = new OffsetDateTimeField(null, 4);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            field = new OffsetDateTimeField(ISOChronology.getInstance().dayOfMonth(), 0);
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
                ISOChronology.getInstance().dayOfMonth(), DateTimeFieldType.dayOfYear(), 4
        );
        assertEquals(DateTimeFieldType.dayOfYear(), field.getType());
        assertEquals(4, field.getOffset());

        try {
            field = new OffsetDateTimeField(null, DateTimeFieldType.dayOfYear(), 4);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            field = new OffsetDateTimeField(ISOChronology.getInstance().dayOfMonth(), null, 3);
            fail();
        } catch (IllegalArgumentException ex) {}

        try {
            field = new OffsetDateTimeField(ISOChronology.getInstance().dayOfMonth(), DateTimeFieldType.dayOfYear(), 0);
            fail();
        } catch (IllegalArgumentException ex) {}
    }


}
