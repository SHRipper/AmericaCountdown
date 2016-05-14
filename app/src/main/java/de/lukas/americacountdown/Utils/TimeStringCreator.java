package de.lukas.americacountdown.Utils;

import android.os.SystemClock;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Lukas on 13.05.2016.
 */
public class TimeStringCreator {
    private static Calendar calendar = Calendar.getInstance(Locale.GERMANY);


    public static String getDateString(){

        String dayOfMonth = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        String[] months = {"Januar","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember"};
        String month = months[calendar.get(Calendar.MONTH )];

        String [] weekdays = {"So.","Mo.","Di.","Mi.","Do.","Fr.","Sa."};
        String weekday = weekdays[calendar.get(Calendar.DAY_OF_WEEK)-1];

        String dateString = weekday + ", " + dayOfMonth + ". " + month;
        return dateString;
    }

    public static String getYearString(){
        return "" + calendar.get(Calendar.YEAR);

    }

    public static String getCurrentTimeInSeconds(){
        calendar.setTimeInMillis(System.currentTimeMillis());
        Calendar flightTime = Calendar.getInstance(Locale.GERMANY);
        flightTime.setTimeInMillis(System.currentTimeMillis());
        flightTime.set(Calendar.DAY_OF_MONTH,11);
        flightTime.set(Calendar.MONTH, Calendar.SEPTEMBER);
        flightTime.set(Calendar.HOUR_OF_DAY, 10);
        flightTime.set(Calendar.MINUTE, 40);
        flightTime.set(Calendar.SECOND, 0);

        long timeInMillis = flightTime.getTimeInMillis() - calendar.getTimeInMillis();
        int seconds, minutes, hours;

        seconds = (int) (timeInMillis / 1000) % 60;
        minutes = (int) (timeInMillis / (1000 * 60)) % 60;
        hours = (int) (timeInMillis / (1000*60*60));

        return hours +":"+ minutes+":"+seconds;
    }
}
