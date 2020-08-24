package com.example.timerapp;

public class Counter {
    private static String time;

    public Counter(String time){
        this.time=time;
    }

    public Counter(){

    }

    public String getTime() {
        return time;
    }

    public  void setTime(String time) {
        this.time = time;
    }
}
