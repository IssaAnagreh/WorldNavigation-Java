package com.worldNavigator;

import java.util.Observable;
import java.util.Observer;

public class PlayerViewer implements Observer {
    public final PlayerControllerInterface playerController;
    public final String name;

    public PlayerViewer(PlayerControllerInterface playerController, String name) {
        this.playerController = playerController;
        this.name = name;
        this.playerController.subscribe(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        PlayerModel playerModel = (PlayerModel) o;
        String msg = (String) arg;
        System.out.println(msg);
    }
}
