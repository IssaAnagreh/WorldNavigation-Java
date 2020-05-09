package com.worldNavigator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public Maps maps;
    public int map_index;
    public PlayerControllerInterface player;
    public PlayerModel playerModel;
    public Commands commands;

    public Menu(Maps maps) {
        this.commands = new Commands();
        this.maps = maps;
        this.map_chooser();
    }

    public void map_chooser() {
        System.out.println("Maps: ");
        int counter = 0;
        while (counter != this.maps.maps.size()) {
            System.out.println(counter + ": " + this.maps.maps.get(counter));
            ++counter;
        }
        System.out.println("Enter map number");
        Scanner sc = new Scanner(System.in);
        this.map_index = sc.nextInt();
    }

    public void preparePlayer(MapFactory map) {
        this.playerModel = new PlayerModel(map, this);
        this.player = new PlayerController(playerModel);
        PlayerViewer playerViewer = new PlayerViewer(this.player, "Isa");
    }

    public void start() throws IOException {
        preparePlayer(this.maps.maps.get(this.map_index));
        this.player.startGame();
    }

    public void restart() throws IOException {
        String map_name = this.maps.maps.get(this.map_index).mapName;
        MapFactory new_map = this.maps.generate(map_name);
        this.maps.replace(new_map, this.map_index);

        preparePlayer(this.maps.maps.get(0));
        player.startGame();
    }

    public void quit() {
        System.exit(1);
    }

    public ArrayList get_commands() {
        return this.commands.getCommands();
    }

    public void add_command(String command) {
        this.commands.addCommand(command);
        this.player = new CustomPlayerController(this.playerModel);
    }

    public String use_command(String command) {
        if (this.commands.getCommands().contains(command)) {
            return command;
        }
        return "";
    }
}
