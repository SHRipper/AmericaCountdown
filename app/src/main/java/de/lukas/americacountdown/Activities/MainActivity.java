package de.lukas.americacountdown.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import de.lukas.americacountdown.Core.InitAlarmManager;
import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Utils.Calculator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatePicker datePicker;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setTimeInMillis(System.currentTimeMillis());


        TextView txtYear = (TextView) findViewById(R.id.txtYear);
        TextView txtDate = (TextView) findViewById(R.id.txtDate);

        txtYear.setText("" + calendar.get(Calendar.YEAR));
        txtDate.setText(getDateString());




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        TextView txtDaysLeft = (TextView) findViewById(R.id.txtDays);
        txtDaysLeft.setText(Calculator.getLeftDays());

        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_key_display_notifications), true)) {
            InitAlarmManager.setAlarmManager(this);
        } else {
            Log.d("InitAlarmManager", "Notifications are disabled. No alarm was set.");
        }


    }

    private String getDateString(){

        String dayOfMonth = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        String[] months = {"Januar","Februar","März","April","Mai","Juni","Juli","August","September","Oktober","November","Dezember"};
        String month = months[calendar.get(Calendar.MONTH ) -1];

        String [] weekdays = {"So.","Mo.","Di.","Mi.","Do.","Fr.","Sa."};
        String weekday = weekdays[calendar.get(Calendar.DAY_OF_WEEK)-1];

        String dateString = weekday + ", " + dayOfMonth + ". " + month;
        return dateString;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_countdown) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}