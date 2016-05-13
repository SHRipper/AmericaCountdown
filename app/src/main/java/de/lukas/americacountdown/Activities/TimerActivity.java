package de.lukas.americacountdown.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Utils.DateStringCreator;

public class TimerActivity extends AppCompatActivity {

    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    private int delay = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        TextView txtYear = (TextView) findViewById(R.id.txtYear);
        TextView txtDate = (TextView) findViewById(R.id.txtDate);
        TextView txtTimer = (TextView) findViewById(R.id.txtTimer);

        txtYear.setText(DateStringCreator.getYearString());
        txtDate.setText(DateStringCreator.getDateString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopHandler();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        startHandler();
    }

    private void startHandler() {
        handler.postDelayed(runnable, delay);
    }

    private void stopHandler() {
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, delay);

            Log.d("Timer", "Timer tick");
        }
    };


}
