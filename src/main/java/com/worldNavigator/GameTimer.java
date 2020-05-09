package com.worldNavigator;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends PlayerController {
    Timer timer;
    public boolean cancel = false;
    private int time = 0;
    public int seconds;
    public int remaining_time;
    private final int milli_in_sec = 1000;
    private final int sec_in_min = 60;

    public GameTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new FinishTask(), milli_in_sec*seconds);
        timer.schedule(new RemindTask(), 0, milli_in_sec);
        System.out.println("This game will end in " + seconds + " seconds");
        this.seconds = seconds;
    }

    public void getRemaining_time() {
        System.out.println("Remaining time: " + remaining_time + " minutes");
    }

    class RemindTask extends TimerTask {
        public void run() {
            int new_remaining_time = (seconds/sec_in_min) - (++time/sec_in_min);
            if (remaining_time != new_remaining_time) {
                remaining_time = new_remaining_time;
            }
        }
    }

    class FinishTask extends TimerTask {
        public void run() {
            System.out.println("Time's up!");
            timer.cancel(); //Terminate the timer thread
            System.exit(1);
            quit();
        }
    }
}