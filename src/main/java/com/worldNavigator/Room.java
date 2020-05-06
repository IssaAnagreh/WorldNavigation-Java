package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Room {
    public HashMap<String, Wall> walls = new HashMap<String, Wall>();
    public String roomName;
    public Boolean lit;
    public Boolean lightSwitch;
    public final int room_number;

    public Room(JSONObject room, int room_counter) {
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
        this.lightSwitch = (room.get("switch") != null) && Boolean.parseBoolean(room.get("switch").toString());
        this.room_number = room_counter;
    }

    public void switchLights() {
        if (this.lightSwitch) {
            this.lit = !this.lit;
            if (lit) {
                System.out.println("Room is lit now");
            } else {
                System.out.println("Room is dark now");
            }
        } else {
            System.out.println("This room has now lights switch, use a flash light");
        }
    }

    public int useFlashLight(int flashLights) {
        if (!this.lit) {
            this.lit = true;
            System.out.println("Room is lit now");
            return --flashLights;
        } else {
            return flashLights;
        }
    }

    @Override
    public String toString() {
        return "You are in room: " + roomName;
    }
}
