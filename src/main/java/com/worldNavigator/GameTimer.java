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

    public GameTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new FinishTask(), 1000*seconds);
        timer.schedule(new RemindTask(), 0, 1000);
        System.out.println("This game will end in " + seconds + " seconds");
        this.seconds = seconds;
    }

    public void getRemaining_time() {
        System.out.println("Remaining time: " + remaining_time + " minutes");
    }

    class RemindTask extends TimerTask {
        public void run() {
            time += 5;
            int new_remaining_time = (seconds/60) - (++time/60);
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
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}