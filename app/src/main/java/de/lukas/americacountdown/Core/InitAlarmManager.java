package de.lukas.americacountdown.Core;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Receiver.MyAlarmSchedulerReceiver;

/**
 * Alarm needs to be initialized, when app is launched and when device has finished booting
 * see https://developer.android.com/training/scheduling/alarms.html#boot
 */
public class InitAlarmManager {

    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private static Intent alarmIntent;
    private static SharedPreferences sharedPreferences;

    public static void setAlarmManager(Context context) {
        alarmIntent = new Intent(context, MyAlarmSchedulerReceiver.class);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 101, alarmIntent, 0);

        Log.d("InitAlarmManager", "initializing alarm manager");

        boolean alarmAlreadySet = (PendingIntent.getBroadcast(context, 101, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmAlreadySet) {

            Log.d("InitAlarmManager", "alarm was not set before. Setting Alarm");
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getAlarmMillis(context), AlarmManager.INTERVAL_DAY, pendingIntent);

        } else {
            Log.d("InitAlarmManager", "alarm was set before. do nothing.");
        }
    }

    public static void cancelAlarmManager(Context context){
        alarmIntent = new Intent(context, MyAlarmSchedulerReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 101, alarmIntent, 0);
        alarmManager.cancel(pendingIntent);
    }

    //alarm um 6:30
    private static long getAlarmMillis(Context context) {
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(System.currentTimeMillis());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String triggerTime = sharedPreferences.getString("key_pref_trigger_time", "6 30");
        Log.d("InitAlarmManager","triggerTime: " + triggerTime);
        String [] parts = triggerTime.split(" ");

        int hour = Integer.valueOf(parts[0]);
        int minute = Integer.valueOf(parts[1]);
        Log.d("InitAlarmManager","hour: " + hour + " minutes: " + minute);

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