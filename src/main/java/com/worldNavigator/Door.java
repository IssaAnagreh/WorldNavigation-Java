package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Door extends Openable implements ItemsContainer {
    public final String name;
    private final Boolean golden;
    private final String nextRoom;
    public String location;

    public Door(JSONObject door) {
        this.name = (String) door.get("name");
        this.golden = door.get("golden").equals("true");
        if (door.get("key") != null) {
            setKey(new Key((String) door.get("key")));
        }
        initIs_locked(door.get("is_locked").equals("true"));
        this.nextRoom = (String) door.get("to");
        this.location = (String) door.get("location");
    }

    public String getLocation() {
        return location;
    }

    public String getNextRoom() {
        if (getIs_locked()) {
            return "";
        } else {
            if (this.getGolden()) {
                System.out.println("CONGRATULATIONS! YOU WON THE GAME");
                System.exit(1);
            };
            if (nextRoom.equals("")) System.out.println("This door opens to nothing");
            return nextRoom;
        }
    }

    @Override
    public HashMap check_content(String location) {
        HashMap<String, Object> content = new HashMap<String, Object>();
        return content;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDetails() {
        return name + " in " + location;
    }

    public Boolean getGolden() {
        return golden;
    }

    @Override
    public String toString() {
        return "door";
    }
}
