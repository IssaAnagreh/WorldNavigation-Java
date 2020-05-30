package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RoomsTest {
  MapFactory map;
  List<Room> rooms;

  public RoomsTest() {
    Maps maps = new Maps();
    this.map = maps.generate("map.json");
    this.rooms = this.map.rooms;
  }

  @Test
  public void roomsWallsExistenceTest() {
    for (Room room : this.rooms) {
      assertFalse(room.walls.isEmpty());
    }
  }
}
