package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Room {
  public HashMap<String, Wall> walls = new HashMap<String, Wall>();
  public final String ROOM_NAME;
  public Boolean isLit;
  public Boolean lightSwitch;
  public final int ROOM_NUMBER;

  public Room(JSONObject room, int room_counter) {
    this.ROOM_NAME = (String) room.get("name");
    this.ROOM_NUMBER = room_counter;
    generateRoom(room);
  }

  private void generateRoom(JSONObject room) {

    Wall north_wall = new Wall("north_wall", (JSONObject) room.get("n_wall"));
    Wall east_wall = new Wall("east_wall", (JSONObject) room.get("e_wall"));
    Wall south_wall = new Wall("south_wall", (JSONObject) room.get("s_wall"));
    Wall west_wall = new Wall("west_wall", (JSONObject) room.get("w_wall"));

    walls.put("n", north_wall);
    walls.put("e", east_wall);
    walls.put("s", south_wall);
    walls.put("w", west_wall);

    this.isLit = Boolean.parseBoolean(room.get("lit").toString());
    this.lightSwitch =
        (room.get("switch") != null) && Boolean.parseBoolean(room.get("switch").toString());
  }

  public void switchLights(PlayerModel playerModel) {
    if (this.lightSwitch) {
      this.isLit = !this.isLit;
      if (this.isLit) {
        playerModel.notify_player("Room is lit now");
      } else {
        playerModel.notify_player("Room is dark now");
      }
    } else {
      playerModel.notify_player("This room has now lights switch, use a flash light");
    }
  }

  public int useFlashLight(int flashLights, PlayerModel playerModel) {
    if (!this.isLit) {
      this.isLit = true;
      playerModel.notify_player("Room is lit now");
      return --flashLights;
    } else {
      return flashLights;
    }
  }

  @Override
  public String toString() {
    return "You are in room: " + this.ROOM_NAME;
  }
}
