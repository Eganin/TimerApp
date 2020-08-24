package com.example.timerapp;

import static com.example.timerapp.MainActivity.endOfTime;

public class AssertEndTime implements Runnable  {
    @Override
    public void run() {
        while (true) {
            // Если время закончилось
            MainActivity mainActivity = new MainActivity();
            if (String.valueOf(mainActivity.textView.getText()).equals(endOfTime)) {
                mainActivity.initMediaPlayer();
                mainActivity.play();
            }
        }
    }


}