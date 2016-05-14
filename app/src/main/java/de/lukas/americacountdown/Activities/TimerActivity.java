package de.lukas.americacountdown.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Utils.TimeStringCreator;

public class TimerActivity extends AppCompatActivity {

    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    private int delay = 1000;
    TextView txtYear;
    TextView txtDate;
    TextView txtHour;
    TextView txtMinute;
    TextView txtSecond;
    String[] parts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        txtYear = (TextView) findViewById(R.id.txtYear);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtHour = (TextView) findViewById(R.id.txtHours);
        txtMinute = (TextView) findViewById(R.id.txtMinutes);
        txtSecond = (TextView) findViewById(R.id.txtSeconds);

        setTimer();

        txtYear.setText(TimeStringCreator.getYearString());
        txtDate.setText(TimeStringCreator.getDateString());
    }

    private void setTimer() {
        String time = TimeStringCreator.getCurrentTimeInSeconds();
        parts = time.split(":");

        String second, minute, hour;


        if (parts[0].equals("1")) {
            hour = " Stunde";
        } else {
            hour = " Stunden";
        }

        if (parts[1].equals("1")) {
            minute = " Minute";
        } else {
            minute = " Minuten";
        }

        if (parts[2].equals("1")) {
            second = " Sekunde";
        } else {
            second = " Sekunden";
        }
        txtHour.setText(parts[0] + hour);
        txtMinute.setText(parts[1] + minute);
        txtSecond.setText(parts[2] + second);
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

            setTimer();
        }
    };


}
