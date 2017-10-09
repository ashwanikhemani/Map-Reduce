import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.IOException;

public class TestJoda {

    public static void main(String [] args) throws IOException , InterruptedException{

        LocalDate date = new LocalDate(2017, 9, 15);

        int year = date.getYear();
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();

        System.out.println(day+":"+month+":"+ year);
        for (int i = 0;i < 100;i++) {
            //Pause for 5 seconds
            Thread.sleep(5000);
            //Print a message
            System.out.println(day+":"+month+":"+ year);
        }


    }
}
