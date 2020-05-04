package com.worldNavigator;

public class Player {
    public String name;
    public String map;
    public int golds;
    public String orientation = "n";
    public String location = "c3";

    public Player(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setGolds(int golds) {
        this.golds = golds;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
