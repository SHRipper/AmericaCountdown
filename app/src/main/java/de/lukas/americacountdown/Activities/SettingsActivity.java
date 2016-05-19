package de.lukas.americacountdown.Activities;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.ListPreference;
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

import de.lukas.americacountdown.Core.InitAlarmManager;
import de.lukas.americacountdown.Preferences.TimePreference;
import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Receiver.BootReceiver;


/**
 * Created by Lukas on 10.05.2016.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    SharedPreferences sharedPreferences;
    TimePreference timePreference;
    ListPreference startFragmentPreference;
    SwitchPreference showNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_main);

        sharedPreferences = getPreferenceManager().getSharedPreferences();

        showNotifications = (SwitchPreference) findPreference(getString(R.string.pref_key_display_notifications));
        showNotifications.setOnPreferenceClickListener(this);

        timePreference = (TimePreference) findPreference("pref_key_trigger_time");
        timePreference.setOnPreferenceChangeListener(this);

        startFragmentPreference = (ListPreference) findPreference("pref_key_start_fragment");
        startFragmentPreference.setOnPreferenceChangeListener(this);

        setShowNotificationsPrefSummary();

        startFragmentPreference.setSummary(sharedPreferences.getString(getString(R.string.pref_key_start_fragment),"null"));
    }

    private void setShowNotificationsPrefSummary(){
        if (showNotifications.isChecked()) {
            showNotifications.setSummary("Anzeigen");
        } else {
            showNotifications.setSummary("Verbergen");
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Add a custom toolbar to the activity
        LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
        Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar_settings, root, false);
        bar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_toolbar_text));
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
        if (preference.getKey().equals(getString(R.string.pref_key_display_notifications))) {
            boolean isChecked = ((SwitchPreference) preference).isChecked();


            if (isChecked) {
                InitAlarmManager.setAlarmManager(this);
                enableBootReceiver();

                preference.setSummary("Anzeigen");
            } else {
                InitAlarmManager.cancelAlarmManager(this);
                disableBootReceiver();

                preference.setSummary("Verbergen");

            }
            Log.d("Preferences", "" + sharedPreferences.getBoolean(getString(R.string.pref_key_display_notifications), true));
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
        if (preference.getKey().equals("pref_key_trigger_time")) {
            Log.d("SettingsActivity", "pref change trigger time");
            preference.setSummary(timePreference.getSummary());

            InitAlarmManager.cancelAlarmManager(this);
            InitAlarmManager.setAlarmManager(this);
        }
        if (preference.getKey().equals("pref_key_start_fragment")) {
            Log.d("SettingsActivity", "new Value: " + newValue);
            sharedPreferences.edit().putString("pref_key_start_fragment", (String)newValue).apply();
            preference.setSummary((String) newValue);
        }
        return false;
    }
}


