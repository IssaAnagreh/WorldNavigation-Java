package com.worldNavigator;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends PlayerController {
    Timer timer;
    public boolean cancel = false;

    public GameTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
        System.out.println("This game will end in " + seconds + " seconds");
    }

    class RemindTask extends TimerTask {
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