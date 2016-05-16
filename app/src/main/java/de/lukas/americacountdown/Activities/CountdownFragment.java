package de.lukas.americacountdown.Activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import de.lukas.americacountdown.Core.InitAlarmManager;
import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Utils.Calculator;
import de.lukas.americacountdown.Utils.TimeStringCreator;

/**
 * Created by Lukas on 16.05.2016.
 */
public class CountdownFragment extends Fragment {

    DatePicker datePicker;
    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main,container,false);


        calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setTimeInMillis(System.currentTimeMillis());


        TextView txtYear = (TextView) view.findViewById(R.id.txtYear);
        TextView txtDate = (TextView) view.findViewById(R.id.txtDate);

        txtYear.setText(TimeStringCreator.getYearString());
        txtDate.setText(TimeStringCreator.getDateString());

        TextView txtDaysLeft = (TextView) view.findViewById(R.id.txtDays);
        txtDaysLeft.setText(Calculator.getLeftDays());

        if(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getString(R.string.pref_key_display_notifications), true)) {
            InitAlarmManager.setAlarmManager(getContext());
        } else {
            Log.d("InitAlarmManager", "Notifications are disabled. No alarm was set.");
        }
        return  view;
    }
}
