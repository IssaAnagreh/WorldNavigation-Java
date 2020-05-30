package com.worldNavigator;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRooms {
  MapFactory map;
  List<Room> rooms;

  public TestRooms() {
    Maps maps = new Maps();
    this.map = maps.generate("map.json");
    this.rooms = this.map.rooms;
  }

  @Test
  public void testWalls() {
    for (Room room : this.rooms) {
      assertFalse(room.walls.isEmpty());
    }
  }
}
