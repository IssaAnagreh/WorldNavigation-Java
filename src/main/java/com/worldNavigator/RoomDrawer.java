package com.worldNavigator;

import java.util.HashMap;

public class RoomDrawer {
  private HashMap<Integer, String> mapStrings = new HashMap<>();
  private String location;
  private Room room;

  public RoomDrawer(String location, Room room) {
    if (room.isLit) {
      this.mapStrings.put(0, "a");
      this.mapStrings.put(1, "b");
      this.mapStrings.put(2, "c");
      this.mapStrings.put(3, "d");
      this.mapStrings.put(4, "e");

      this.location = location;
      this.room = room;

      loop(1);
      loop(2);
      loop(3);
      loop(4);
      loop(5);
    }
  }

  private void loop(int num) {
    for (int i = 0; i < 5; ++i) {
      if (this.location.equals(mapStrings.get(i) + num)) {
        System.out.print("░░ ");
      } else {
        boolean item = false;
        for (Wall wall : this.room.walls.values()) {
          if (!item) {
            item =
                !wall.itemsFactory
                    .checkItemByLocation(mapStrings.get(i) + num)
                    .equals("Nothing in this location");
          }
        }
        System.out.print(!item ? mapStrings.get(i) + num + " " : "■■ ");
      }
    }
    System.out.println();
  }
}
