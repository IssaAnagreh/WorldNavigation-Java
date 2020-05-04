package com.worldNavigator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Maps maps = new Maps();
        maps.addMap("map.json");

        PlayerController player = new PlayerController(maps.maps.get(0));
        PlayerViewer playerViewer = new PlayerViewer(player, "Isa");
        player.startGame();
    }
}
