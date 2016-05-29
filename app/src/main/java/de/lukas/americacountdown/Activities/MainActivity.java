package de.lukas.americacountdown.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import de.lukas.americacountdown.Fragments.CountdownFragment;
import de.lukas.americacountdown.Fragments.TimerFragment;
import de.lukas.americacountdown.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager = getSupportFragmentManager();

    boolean canExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        navigationView.setNavigationItemSelectedListener(this);


        setStartFragment();
    }

    private void setStartFragment() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String startFragment = sharedPreferences.getString(getString(R.string.pref_key_start_fragment), getString(R.string.pref_default_start_fragment));
        Fragment fragment = null;

        Log.d("MainActivity", "start fragment: " + startFragment);

        switch (startFragment) {
            case "Countdown":
                fragment = new CountdownFragment();
                break;
            case "Timer":
                fragment = new TimerFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if (canExit) {
                super.onBackPressed();
            } else {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.message_on_back_press), Toast.LENGTH_SHORT).show();
                canExit = true;
                Handler onBackPressHandler = new Handler();
                onBackPressHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canExit = false;
                    }
                }, 3000);
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_countdown) {
            fragment = new CountdownFragment();
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_timer) {
            fragment = new TimerFragment();
        }

        // Replace current content with the fragment content
        try {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // set actionbar title for different fragments
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


}