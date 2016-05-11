package de.lukas.americacountdown.Activities;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
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

import de.lukas.americacountdown.Core.InitAlarmManager;
import de.lukas.americacountdown.Preferences.TimePreference;
import de.lukas.americacountdown.R;


/**
 * Created by Lukas on 10.05.2016.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        SwitchPreference showNotifications = (SwitchPreference) findPreference(getString(R.string.pref_key_display_notifications));
        showNotifications.setOnPreferenceClickListener(this);
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
            editor.putBoolean(getString(R.string.pref_key_display_notifications), checked);
            editor.commit();

            if (checked){
                InitAlarmManager.setAlarmManager(this);
            }else{
                InitAlarmManager.cancelAlarmManager(this);
            }
            Log.d("Preferences","" + sharedPreferences.getBoolean(getString(R.string.pref_key_display_notifications), false));
        }
        return false;
    }
}


