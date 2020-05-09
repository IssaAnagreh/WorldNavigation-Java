package com.worldNavigator;

import java.util.ArrayList;
import java.util.List;

public class Commands {
    private ArrayList<String> commands = new ArrayList<>();

    public Commands() {
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

    public ArrayList getCommands() {
        return this.commands;
    }

    public void addCommand(String command) {
        this.commands.add(command);
    }

    @Override
    public String toString() {
        return this.commands.toString();
    }
}
