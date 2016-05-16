package de.lukas.americacountdown.Activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.lukas.americacountdown.R;
import de.lukas.americacountdown.Utils.TimeStringCreator;

/**
 * Created by Lukas on 16.05.2016.
 */
public class TimerFragment extends Fragment {

    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();
    private int delay = 1000;
    TextView txtYear;
    TextView txtDate;
    TextView txtHour;
    TextView txtMinute;
    TextView txtSecond;
    String[] parts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_timer, container, false);
        txtYear = (TextView) view.findViewById(R.id.txtYear);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtHour = (TextView) view.findViewById(R.id.txtHours);
        txtMinute = (TextView) view.findViewById(R.id.txtMinutes);
        txtSecond = (TextView) view.findViewById(R.id.txtSeconds);

        setTimer();

        txtYear.setText(TimeStringCreator.getYearString());
        txtDate.setText(TimeStringCreator.getDateString());

        return view;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        startHandler();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopHandler();
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
