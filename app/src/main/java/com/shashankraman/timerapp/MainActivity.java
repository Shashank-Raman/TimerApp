package com.shashankraman.timerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button timerButton;
    CountDownTimer countDownTimer;
    boolean counterIsActive = false;

    private void updateTimer(int secondsLeft)
    {
        int minutes = secondsLeft/ 60;
        int seconds = secondsLeft % 60;
        String minuteString = "";
        String secondString = "";

        if(minutes == 0) minuteString = "00";
        else if(minutes < 10) minuteString = "0" + minutes;
        else minuteString = minutes + "";

        if(seconds == 0) secondString = "00";
        else if(seconds < 10) secondString = "0" + seconds;
        else secondString = seconds + "";

        timerTextView.setText(minuteString + ":" + secondString);
    }

    public void controlTimer(View view)
    {
        if(!counterIsActive)
        {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            timerButton.setText("Stop Timer");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("00:00");
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                }
            }.start();
        }
        else
        {
            timerTextView.setText("00:30");
            timerSeekBar.setProgress(30);
            countDownTimer.cancel();
            timerButton.setText("Start Timer");
            timerSeekBar.setEnabled(true);
            counterIsActive = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerButton = (Button) findViewById(R.id.timerButton);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*
        new CountDownTimer(10000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                Toast.makeText(MainActivity.this, "" + (millisUntilFinished / 1000), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish()
            {
                Toast.makeText(MainActivity.this, "Counter is done!!!", Toast.LENGTH_SHORT).show();
            }
        }.start();
        */

        /*
        final Handler handler = new Handler();
        final int counter = 0;
        Runnable run = new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText(MainActivity.this, "This will run every second!", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);
        */
    }
}
