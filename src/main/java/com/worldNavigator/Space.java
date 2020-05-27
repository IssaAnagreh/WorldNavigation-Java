package com.worldNavigator;

public class Space extends Item {
    private final String NAME;

    public Space() {
        this.NAME = "Space";
    }

    @Override
    public String getLocation() {
        return "Space";
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    @Override
    public String getType() {
        return "nothing";
    }

    @Override
    public int compareTo(String location) {
        return this.getLocation().compareTo(location);
    }

    @Override
    public String toString() {
        return this.NAME;
    }
}