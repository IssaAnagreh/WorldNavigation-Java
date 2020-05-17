package com.worldNavigator;

import java.io.IOException;
import java.util.*;

public class PlayerControllerMaster implements PlayerControllerInterface {
    PlayerModel playerModel;
    private ArrayList<String> commands = new ArrayList<>();
    private ArrayList<String> door_commands = new ArrayList<>();
    private ArrayList<String> chest_commands = new ArrayList<>();
    private ArrayList<String> mirror_commands = new ArrayList<>();
    private ArrayList<String> painting_commands = new ArrayList<>();
    private ArrayList<String> seller_commands = new ArrayList<>();
    private boolean hint = true;
//    private HashMap<String, Runnable> commandsMap = new HashMap();

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
        String checkString = "check";
        commands.add(checkString);
        chest_commands.add(checkString);
        mirror_commands.add(checkString);
        painting_commands.add(checkString);
        commands.add("c");
        commands.add("myItems");
        commands.add("items");
        String useKeyString = "useKey";
        commands.add(useKeyString);
        chest_commands.add(useKeyString);
        door_commands.add(useKeyString);
        commands.add("key");
        commands.add("open");
        door_commands.add("open");
        String tradeString = "trade";
        commands.add(tradeString);
        seller_commands.add(tradeString);
        commands.add("switchLight");
        commands.add("switch");
        commands.add("flashLight");
        commands.add("flash");
        commands.add("time");
        commands.add("hints");
        commands.add("quit");
    }

    public void subscribe(PlayerViewer playerViewer) {
        this.playerModel.addObserver(playerViewer);
//        commandsMap.put("wall", this::wall);
//        commandsMap.get("wall").run();
    }

    public void startGame() throws IOException {
        this.playerModel.startGame();

        // commands entering method
        while (this.playerModel.playing) {
            System.out.print("Enter your next command: ");
            String command = this.playerModel.br.readLine();
            if (this.playerModel.playing) use_method(command.trim().toLowerCase());
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
        this.playerModel.notify_player(this.playerModel.location);
    }

    public void myOrientation() {
        this.playerModel.notify_player(this.playerModel.orientation);
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
        this.playerModel.notify_player(this.playerModel.room.toString());
    }

    public void check() {
        this.playerModel.check();
    }

    public String getType() {
        return this.playerModel.getType();
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
        String type = this.getType();
        switch (type+"") {
            case "door":
                System.out.println("here");
                this.playerModel.notify_player(this.door_commands.toString());
                break;
            case "seller":
                this.playerModel.notify_player(this.seller_commands.toString());
                break;
            case "mirror":
                this.playerModel.notify_player(this.mirror_commands.toString());
                break;
            case "painting":
                this.playerModel.notify_player(this.painting_commands.toString());
                break;
            case "chest":
                this.playerModel.notify_player(this.chest_commands.toString());
                break;
            default:
                this.playerModel.notify_player(this.commands.toString());
        }
    }

    public void switchHints() {
        this.hint = !this.hint;
        this.playerModel.notify_player("Hints are " + (this.hint ? "on" : "off"));
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
        String hint = ", use <hints> command to stop these hints";
        switch (command) {
            case "room":
                room();
                break;
            case "orientation":
                myOrientation();
                if (this.hint) this.playerModel.notify_player("You can use <o> as a shortcut command" + hint);
                break;
            case "o":
                myOrientation();
                break;
            case "location":
                myLocation();
                if (this.hint) this.playerModel.notify_player("You can use <loc> as a shortcut command" + hint);
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
                if (this.hint) this.playerModel.notify_player("You can use <l> as a shortcut command" + hint);
                break;
            case "l":
                rotateLeft();
                break;
            case "right":
                rotateRight();
                if (this.hint) this.playerModel.notify_player("You can use <r> as a shortcut command" + hint);
                break;
            case "r":
                rotateRight();
                break;
            case "forward":
                move(PlayerControllerMaster.MoveParam.forward);
                if (this.hint) this.playerModel.notify_player("You can use <f> as a shortcut command" + hint);
                break;
            case "f":
                move(PlayerControllerMaster.MoveParam.forward);
                break;
            case "backward":
                move(PlayerControllerMaster.MoveParam.backward);
                if (this.hint) this.playerModel.notify_player("You can use <b> as a shortcut command" + hint);
                break;
            case "b":
                move(PlayerControllerMaster.MoveParam.backward);
                break;
            case "check":
                check();
                acquire_items();
                if (this.hint) this.playerModel.notify_player("You can use <c> as a shortcut command" + hint);
                break;
            case "c":
                check();
                acquire_items();
                break;
            case "myItems":
                myItems();
                if (this.hint) this.playerModel.notify_player("You can use <items> as a shortcut command" + hint);
                break;
            case "items":
                myItems();
                break;
            case "useKey":
                use_key();
                if (this.hint) this.playerModel.notify_player("You can use <key> as a shortcut command" + hint);
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
                if (this.hint) this.playerModel.notify_player("You can use <light> as a shortcut command" + hint);
                break;
            case "light":
                switchLights();
                break;
            case "flashLight":
                flashLight();
                if (this.hint) this.playerModel.notify_player("You can use <flash> as a shortcut command" + hint);
                break;
            case "flash":
                flashLight();
                break;
            case "commands":
                commands();
                break;
            case "hints":
                switchHints();
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
                this.playerModel.notify_player("You should use a valid command");
                break;
        }
    }

    @Override
    public String toString() {
        return "Player Controller Master";
    }
}
