package de.lukas.americacountdown.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lukas on 07.05.2016.
 */
public class Calculator {

    public static String getLeftDays() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String endDate = "11 09 2016";
        Calendar c = Calendar.getInstance();

        long daysLeft = 0;
        try {
            Date date1 = c.getTime();
            Date date2 = myFormat.parse(endDate);
            daysLeft = date2.getTime() - date1.getTime();
            daysLeft = TimeUnit.DAYS.convert(daysLeft, TimeUnit.MILLISECONDS);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return (daysLeft + "");
    }
}
