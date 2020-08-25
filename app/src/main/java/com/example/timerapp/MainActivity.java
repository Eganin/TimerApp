package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public MediaPlayer mediaPlayer;
    public TextView textView;
    public Button startButton;
    private boolean isTimerOn;// флаг который показывает работает ли сейчас таймер
    private CountDownTimer countDownTimer;
    private final static String textStartTimer = "START";
    private final static String textStopTimer = "STOP";
    private final static String textViewEndTimer = "00:00";
    private final static int interval = 1000;
    private final static int maxSecondsTimer = 3599;
    private final static int defaultProgress = 59;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        startButton = findViewById(R.id.startButton);

        seekBar.setMax(maxSecondsTimer);
        seekBar.setProgress(defaultProgress);
        isTimerOn = false;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setTimerSeekBar(i);// установка времени с помощью seekbar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void startTimer(View view) {
        if(!isTimerOn){
            startButton.setText(textStopTimer);
            /*
            С помощью метода .setEnabled(false)
            Мы запрещаем пользователю воздействоавть
            на seek bar
             */
            seekBar.setEnabled(false);
            isTimerOn = true;

            // получаем текущее время
            Integer currentTimeStart = getCurrentTime() * 1000;// получаем текущее время в милисекндах
            countDownTimer = new CountDownTimer(currentTimeStart, interval) {
                @Override
                public void onTick(long l) {
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    sound();
                    resetTimer();
                }
            };
            countDownTimer.start();
        }else{
            countDownTimer.cancel();// останавливаем таймер
            startButton.setText(textStartTimer);

            seekBar.setEnabled(true); // делаем seek bar доступным
            isTimerOn = false;
        }

    }

    private void resetTimer(){
        countDownTimer.cancel();// останавливаем таймер
        startButton.setText(textStartTimer);
        textView.setText(R.string.timer_default);
        seekBar.setEnabled(true); // делаем seek bar доступным
        seekBar.setProgress(defaultProgress);
        isTimerOn = false;
    }

    private void updateTimer(){
        String currentTime = determinantOfTime(getCurrentTime());
        Log.d("time",currentTime);
        textView.setText(currentTime);
        roadSeekBar();// сдвигаем seekBar назад по истечении метода
    }

    private void sound(){
        initMediaPlayer();
        textView.setText(textViewEndTimer);
        startButton.setText(textStartTimer);
        playSound();
    }

    private void setTimerSeekBar(int progress) {
        /*
        Данный метод устанавливает время с помощтю ползунка
         */
        String resultTime = determinantOfTime(progress);

        textView.setText(resultTime);
    }

    private void roadSeekBar() {
        /*
        устанавливаем на seekbar текущее время
         */
        seekBar.setProgress(getCurrentTime() - 1);
    }


    private Integer getCurrentTime() {
        /*
        Метод возвоащает текущее воемя на таймере в секндах
         */
        Integer currentTimeSecond =
                Integer.parseInt(String.valueOf(textView.getText()).substring(3, 5));
        Integer currentTimeMinute =
                Integer.parseInt(String.valueOf(textView.getText()).substring(0, 2)) * 60;
        Integer total = currentTimeMinute + currentTimeSecond;
        return total;
    }

    private String determinantOfTime(int totalTime) {
        /*
        Метод преобразовывает секундв в строку для вставки в TextView
         */
        String settingTime = null;
        if (totalTime <= 60) {
            if (totalTime < 10){
                settingTime = "00:" +"0"+ totalTime;
            }else{
                settingTime = "00:" + totalTime;
            }
        } else {
            String minute = String.valueOf(totalTime / 60);
            String second = String.valueOf(totalTime - (Integer.parseInt(minute) * 60));

            if (Integer.parseInt(minute) > 9) {
                if (Integer.parseInt(second) < 10) {
                    settingTime = minute + ":" + "0" + second;
                } else {
                    settingTime = minute + ":" + second;
                }
            } else {
                if (Integer.parseInt(second) < 10) {
                    settingTime = "0" + minute + ":" + "0" + second;
                } else {
                    settingTime = "0" + minute + ":" + second;
                }
            }
        }


        return settingTime;
    }

    public void initMediaPlayer() {// инициализация плеера
        mediaPlayer = MediaPlayer.create(getApplicationContext(),
                R.raw.bell_sound);// указываем .mp3 mediaPlayer
    }

    public void playSound() {
        mediaPlayer.start();// проигрываем музыку
    }


}