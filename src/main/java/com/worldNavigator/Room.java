package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Room {
    public HashMap<String, Wall> walls = new HashMap<String, Wall>();
    public String roomName;
    public Boolean lit;

    public Room(JSONObject room) {
        this.roomName = (String) room.get("name");

        Wall north_wall = new Wall("north_wall", (JSONObject) room.get("n_wall"));
        Wall east_wall = new Wall("east_wall", (JSONObject) room.get("e_wall"));
        Wall south_wall = new Wall("south_wall", (JSONObject) room.get("s_wall"));
        Wall west_wall = new Wall("west_wall", (JSONObject) room.get("w_wall"));

        walls.put("n", north_wall);
        walls.put("e", east_wall);
        walls.put("s", south_wall);
        walls.put("w", west_wall);

        this.lit = Boolean.parseBoolean(room.get("lit").toString());
    }


    @Override
    public String toString() {
        return "You are in room: " + roomName;
    }
}
