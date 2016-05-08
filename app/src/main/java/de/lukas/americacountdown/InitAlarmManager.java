package de.lukas.americacountdown;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Alarm needs to be initialized, when app is launched and when device has finished booting
 * see https://developer.android.com/training/scheduling/alarms.html#boot
 */
public class InitAlarmManager {

    public static void setAlarmManager(Context context) {
        Log.d("InitAlarmManager", "initializing alarm manager");
        Intent alarmIntent = new Intent(context, MyAlarmSchedulerReceiver.class);
        boolean alarmAlreadySet = (PendingIntent.getBroadcast(context, 101, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmAlreadySet) {
            Log.d("InitAlarmManager","alarm was not set before. Setting Alarm");
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, alarmIntent,0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getAlarmMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else{
            Log.d("InitAlarmManager", "alarm was set before. do nothing.");
        }
    }

    //alarm um 6:30
    private static long getAlarmMillis() {
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(System.currentTimeMillis());
        int hour = 6;
        int minute = 30;

        // if current time is greater than alarm manager trigger
        // then add one calendar day to avoid notification on startup
        if (cal.get(Calendar.HOUR_OF_DAY) > hour ||
                cal.get(Calendar.HOUR_OF_DAY) == hour &&
                        cal.get(Calendar.MINUTE) > minute) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        Log.d("Calendar", "" + cal.get(Calendar.HOUR_OF_DAY) + " . " + cal.get(Calendar.MINUTE));

        return cal.getTimeInMillis();
    }

}