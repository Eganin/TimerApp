package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String textStartTimer = "START";
    private final static String textStopTimer = "STOP";
    private final static int interval = 1000;
    private final static int maxSecondsTimer = 3599;
    private final static int defaultProgress = 60;
    private TextView textView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        seekBar  = findViewById(R.id.seekBar);

        seekBar.setMax(maxSecondsTimer);
        seekBar.setProgress(defaultProgress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setTimerSeekBar(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setTimerSeekBar(int progress){
        int minutes =progress/60;
        int seconds = progress-(minutes * 60);

        String minutesString ="";
        String secondsString="";

        if(minutes<10){
            minutesString = "0"+ minutes;
        }else{
            minutesString = String.valueOf(minutes);
        }

        if(seconds<10){
            secondsString = "0"+ seconds;
        }else{
            secondsString = String.valueOf(seconds);
        }

        String resultTime = minutesString+":"+secondsString;

        textView.setText(resultTime);
    }

    private void roadSeekBar(){
        seekBar.setProgress(getCurrentTime()-1);
    }


    public void startTimer(View view) {
        // получаем текущее время
        Integer currentTimeStart = getCurrentTime()*1000;
        CountDownTimer myTimer = new CountDownTimer(currentTimeStart, interval) {
            @Override
            public void onTick(long l) {
                String currentTime = determinantOfTime(getCurrentTime()-1);
                textView.setText(currentTime);
                roadSeekBar();// сдвигаем seekBar назад по истечении метода
            }

            @Override
            public void onFinish() {
                Log.d("timer", "End timer");
            }
        };

        myTimer.start();
    }

     private Integer getCurrentTime(){
        Integer currentTimeSecond =
                Integer.parseInt(String.valueOf(textView.getText()).substring(3, 5));
        Integer currentTimeMinute =
                Integer.parseInt(String.valueOf(textView.getText()).substring(0, 2))*60;
        Integer total = currentTimeMinute + currentTimeSecond;
        return total;
    }

    private String determinantOfTime(int totalTime){
        String settingTime = null;
        if(totalTime<=60){
            settingTime = "00:"+totalTime;
        }else{
            String minute = String.valueOf(totalTime / 60);
            String second = String.valueOf(totalTime-(Integer.parseInt(minute) * 60));
            if(minute.length()<1){
                settingTime = "00:"+second;
            }else{
                if(minute.length()>1){
                    if(Integer.parseInt(second)<10){
                        settingTime = minute+":"+"0"+second;
                    }else{
                        settingTime = minute+":"+second;
                    }
                }else{
                    if(Integer.parseInt(second)<10){
                        settingTime = "0"+minute+":"+"0"+second;
                    }else{
                        settingTime = "0"+minute+":"+second;
                    }
                }
            }
        }

        return settingTime;
    }
}