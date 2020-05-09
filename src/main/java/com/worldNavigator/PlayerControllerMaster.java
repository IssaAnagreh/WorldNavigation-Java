package com.worldNavigator;

import java.io.IOException;
import java.util.*;

public class PlayerControllerMaster implements PlayerControllerInterface {
    PlayerModel playerModel;
    private ArrayList<String> commands = new ArrayList<>();

    public PlayerControllerMaster() {
        commands.add("room");
        commands.add("orientation");
        commands.add("o");
        commands.add("location");
        commands.add("loc");
        commands.add("wall");
        commands.add("look");
        commands.add("left");
        commands.add("l");
        commands.add("right");
        commands.add("r");
        commands.add("forward");
        commands.add("f");
        commands.add("backward");
        commands.add("b");
        commands.add("check");
        commands.add("c");
        commands.add("myItems");
        commands.add("items");
        commands.add("useKey");
        commands.add("key");
        commands.add("open");
        commands.add("trade");
        commands.add("switchLight");
        commands.add("switch");
        commands.add("flashLight");
        commands.add("flash");
        commands.add("time");
        commands.add("quit");
    }

    public void subscribe(PlayerViewer playerViewer) {
        this.playerModel.addObserver(playerViewer);
    }

    public void startGame() throws IOException {
        this.playerModel.startGame();

        // commands entering method
        while (this.playerModel.playing) {
            System.out.print("Enter your next command: ");
            String command = this.playerModel.br.readLine();
            if (this.playerModel.playing) use_method(command.trim());
        }
    }

    public void myItems() {
        this.playerModel.myItems();
    }

    public void rotateLeft() {
        this.playerModel.rotateLeft();
    }

    public void rotateRight() {
        this.playerModel.rotateRight();
    }

    public void myLocation() {
        System.out.println(this.playerModel.location);
    }

    public void myOrientation() {
        System.out.println(this.playerModel.orientation);
    }

    public enum MoveParam {
        forward, backward;
    }

    public void move(PlayerControllerMaster.MoveParam move) {
        this.playerModel.move(move);
    }

    public void wall() {
        this.playerModel.wall();
    }

    public void look() {
        this.playerModel.look();
    }

    public void room() {
        System.out.println(this.playerModel.room.toString());
    }

    public void check() {
        this.playerModel.check();
    }

    public void acquire_items() {
        this.playerModel.acquire_items();
    }

    public void use_key() {
        this.playerModel.use_key();
    }

    public void open() {
        this.playerModel.open();
    }

    public void setLocation() {
        this.playerModel.setLocation();
    }

    public void trade() {
        this.playerModel.trade();
    }

    public void switchLights() {
        this.playerModel.switchLights();
    }

    public void flashLight() {
        this.playerModel.flashLight();
    }


    public void time() {
        this.playerModel.timer.getRemaining_time();
    }

    public void commands() {
        System.out.println(this.commands);
    }

    public void restart() {
        try {
            this.playerModel.menu.restart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quit() {
        this.playerModel.menu.quit();
    }


    public void use_method(String command) {
        switch (command) {
            case "room":
                room();
                break;
            case "orientation":
                myOrientation();
                System.out.println("You can use <o> as a shortcut command");
                break;
            case "o":
                myOrientation();
                break;
            case "location":
                myLocation();
                System.out.println("You can use <loc> as a shortcut command");
                break;
            case "loc":
                myLocation();
                break;
            case "wall":
                wall();
                break;
            case "look":
                look();
                break;
            case "left":
                rotateLeft();
                System.out.println("You can use <l> as a shortcut command");
                break;
            case "l":
                rotateLeft();
                break;
            case "right":
                rotateRight();
                System.out.println("You can use <r> as a shortcut command");
                break;
            case "r":
                rotateRight();
                break;
            case "forward":
                move(PlayerControllerMaster.MoveParam.forward);
                System.out.println("You can use <f> as a shortcut command");
                break;
            case "f":
                move(PlayerControllerMaster.MoveParam.forward);
                break;
            case "backward":
                move(PlayerControllerMaster.MoveParam.backward);
                System.out.println("You can use <b> as a shortcut command");
                break;
            case "b":
                move(PlayerControllerMaster.MoveParam.backward);
                break;
            case "check":
                check();
                acquire_items();
                System.out.println("You can use <c> as a shortcut command");
                break;
            case "c":
                check();
                acquire_items();
                break;
            case "myItems":
                myItems();
                System.out.println("You can use <items> as a shortcut command");
                break;
            case "items":
                myItems();
                break;
            case "useKey":
                use_key();
                System.out.println("You can use <key> as a shortcut command");
                break;
            case "key":
                use_key();
                break;
            case "setloc":
                setLocation();
                break;
            case "open":
                open();
                break;
            case "trade":
                trade();
                break;
            case "switchLight":
                switchLights();
                System.out.println("You can use <light> as a shortcut command");
                break;
            case "light":
                switchLights();
                break;
            case "flashLight":
                flashLight();
                System.out.println("You can use <flash> as a shortcut command");
                break;
            case "flash":
                flashLight();
                break;
            case "commands":
                commands();
                break;
            case "time":
                time();
                break;
            case "restart":
                restart();
                break;
            case "quit":
                quit();
                break;
            default:
                System.out.println("You should use a valid command");
                break;
        }
    }
}
