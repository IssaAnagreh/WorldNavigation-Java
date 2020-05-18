package com.worldNavigator;

import java.io.IOException;
import java.util.Map;

public interface PlayerControllerInterface {
    Map<String, Command> commandsMap = null;

    void subscribe(PlayerViewer playerViewer);

    void startGame() throws IOException;

    void myItems();

    void rotateLeft();

    void rotateRight();

    void myLocation();

    void myOrientation();

    enum MoveParam {
        forward, backward;
    }

    void move(PlayerControllerMaster.MoveParam move);

    void wall();

    void look();

    void room();

    void check();

    void acquire_items();

    void use_key();

    void open();

    void setLocation();

    void trade();

    void switchLights();

    void flashLight();

    void time();

    void restart();

    void quit();

    String getType();

    void commands();

    void use_method(String command);

    void switchHints();
}
