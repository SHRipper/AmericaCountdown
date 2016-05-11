package de.lukas.americacountdown.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import de.lukas.americacountdown.Activities.MainActivity;
import de.lukas.americacountdown.Activities.SettingsActivity;
import de.lukas.americacountdown.Core.InitAlarmManager;
import de.lukas.americacountdown.R;

public class TimePreference extends DialogPreference {
    private int lastHour=6;
    private int lastMinute=30;
    private TimePicker picker=null;

    SharedPreferences sharedPreferences;

    public static int getHour(String time) {
        String[] pieces=time.split(" ");

        return(Integer.parseInt(pieces[0]));
    }

    public static int getMinute(String time) {
        String[] pieces=time.split(" ");

        return(Integer.parseInt(pieces[1]));
    }

    public TimePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        setPositiveButtonText("Ok");
        setNegativeButtonText("Abbrechen");
    }

    @Override
    protected View onCreateDialogView() {
        picker=new TimePicker(getContext());

        picker.setIs24HourView(true);

        return(picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        picker.setCurrentHour(lastHour);
        picker.setCurrentMinute(lastMinute);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            lastHour=picker.getCurrentHour();
            lastMinute=picker.getCurrentMinute();

            String time=String.valueOf(lastHour)+" "+String.valueOf(lastMinute);

            // save new time
            sharedPreferences.edit().putString("key_pref_trigger_time", time).commit();


            // cancel the old alarm and set a new one
            InitAlarmManager.cancelAlarmManager(getContext());
            InitAlarmManager.setAlarmManager(getContext());

            Toast.makeText(getContext(), time, Toast.LENGTH_SHORT).show();
            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return(a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time=null;

        if (restoreValue) {
            if (defaultValue==null) {
                time=getPersistedString("00 00");
            }
            else {
                time=getPersistedString(defaultValue.toString());
            }
        }
        else {
            time=defaultValue.toString();
        }

        lastHour=getHour(time);
        lastMinute=getMinute(time);
    }
}