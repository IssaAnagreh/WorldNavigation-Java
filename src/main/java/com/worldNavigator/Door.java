package com.worldNavigator;

import org.json.simple.JSONObject;

public class Door extends Item {
    public final String NAME;
    private final Boolean IS_GOLDEN;
    private final String NEXT_ROOM;
    private final String LOCATION;

    public Door(JSONObject door) {
        this.NAME = (String) door.get("name");
        this.LOCATION = (String) door.get("location");
        this.IS_GOLDEN = door.get("golden").equals("true");
        this.NEXT_ROOM = (String) door.get("to");

        if (door.get("key") != null) {
            super.setUseKeyBehavior(new Openable(door, "Door"));
        }
        super.setCheckBehavior(new Uncheckable());
    }

    public String getNextRoom() {
        if (super.useKeyBehavior.getIs_locked() != null && super.useKeyBehavior.getIs_locked()) {
            return "locked";
        } else {
            if (this.getGolden() != null && this.getGolden()) {
                return "golden";
            }
            return this.NEXT_ROOM;
        }
    }


    private Boolean getGolden() {
        return this.IS_GOLDEN;
    }

    public String getLocation() {
        return this.LOCATION;
    }

    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "door";
    }

    @Override
    public String toString() {
        return "Door: " + this.NAME + ", in " + this.LOCATION;
    }

    @Override
    public int compareTo(String location) {
        return this.getLocation().compareTo(location);
    }
}
