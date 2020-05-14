package com.worldNavigator;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends PlayerController {
    Timer timer;
    private int time = 0;
    public int seconds;
    public int remaining_time;
    private final int MILLI_IN_SEC = 1000;

    public GameTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new FinishTask(), this.MILLI_IN_SEC*seconds);
        timer.schedule(new RemindTask(), 0, this.MILLI_IN_SEC);
        System.out.println("This game will end in " + seconds + " seconds");
        this.seconds = seconds;
    }

    public void getRemaining_time() {
        System.out.println("Remaining time: " + remaining_time + " minutes");
    }

    class RemindTask extends TimerTask {
        private final int SEC_IN_MIN = 60;

        public void run() {
            int new_remaining_time = (seconds/this.SEC_IN_MIN) - (++time/this.SEC_IN_MIN);
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