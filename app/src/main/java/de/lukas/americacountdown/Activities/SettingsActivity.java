package de.lukas.americacountdown.Activities;

import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;
import java.util.prefs.PreferenceChangeListener;

import de.lukas.americacountdown.Core.InitAlarmManager;
import de.lukas.americacountdown.Preferences.TimePreference;
import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Receiver.BootReceiver;


/**
 * Created by Lukas on 10.05.2016.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener{

    SharedPreferences sharedPreferences;
    TimePreference timePreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SwitchPreference showNotifications = (SwitchPreference) findPreference(getString(R.string.pref_key_display_notifications));
        showNotifications.setOnPreferenceClickListener(this);

        timePreference = (TimePreference) findPreference("pref_key_trigger_time");
        timePreference.setOnPreferenceChangeListener(this);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
        bar.setTitleTextColor(ContextCompat.getColor(this,R.color.color_toolbar_text));
        root.addView(bar, 0); // insert at top
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(getString(R.string.pref_key_display_notifications))){
            boolean checked = ((SwitchPreference) preference).isChecked();


            if (checked){
                InitAlarmManager.setAlarmManager(this);
                enableBootReceiver();
            }else{
                InitAlarmManager.cancelAlarmManager(this);
                disableBootReceiver();
            }
            Log.d("Preferences","" + sharedPreferences.getBoolean(getString(R.string.pref_key_display_notifications), true));
        }
        return false;
    }

    //https://developer.android.com/training/scheduling/alarms.html#boot
    private void disableBootReceiver() {
        ComponentName receiver = new ComponentName(this, BootReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void enableBootReceiver() {
        ComponentName receiver = new ComponentName(this, BootReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals("pref_key_trigger_time")){
            Log.d("SettingsActivity", "pref change trigger time");
            preference.setSummary(timePreference.getSummary());

            InitAlarmManager.cancelAlarmManager(this);
            InitAlarmManager.setAlarmManager(this);
        }
        return false;
    }
}


