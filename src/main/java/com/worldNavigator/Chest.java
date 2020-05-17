package com.worldNavigator;

import org.json.simple.JSONObject;

public class Chest extends Item {
    private final String NAME;
    private final String LOCATION;

    public Chest(JSONObject chest) {
        this.NAME = (String) chest.get("name");
        this.LOCATION = (String) chest.get("location");

        if (chest.get("key") != null) {
            super.setUseKeyBehavior(new Openable(chest, "Chest"));
            if (chest.get("existed").equals("true")) {
                super.setCheckBehavior(new Locked_Checkable(chest, this.LOCATION, super.useKeyBehavior));
            }
        } else {
            if (chest.get("existed").equals("true")) {
                super.setCheckBehavior(new Unlocked_Checkable(chest, this.LOCATION));
            }
        }
    }

    public String getLocation() {
        return this.LOCATION;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "chest";
    }

    @Override
    public String toString() {
        return super.useKeyBehavior.getIs_locked() ? "LOCKED Chest: " + this.NAME + ", in: " + this.LOCATION : "UNLOCKED Chest: " + this.NAME + ", in: " + this.LOCATION;
    }

    @Override
    public int compareTo(String location) {
        return this.getLocation().compareTo(location);
    }
}
