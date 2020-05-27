package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.*;

public class ItemsFactory {
  public Map<String, Item> items = new HashMap<>();
  private String existed = "existed";

  public ItemsFactory(JSONObject wall) {
    prepareDoor(wall);
    prepareChest(wall);
    prepareMirror(wall);
    prepareSeller(wall);
    preparePainting(wall);
    prepareSafe(wall);
    prepareGate(wall);
    prepareTable(wall);
    prepareWindow(wall);
  }

  private void prepareDoor(JSONObject wall) {
    Object door = wall.get("door");
    if (door != null && (((JSONObject) door).get(existed)).equals("true")) {
      items.put("door", new Door((JSONObject) door));
    }
  }

  private void prepareChest(JSONObject wall) {
    Object chest = wall.get("chest");
    if (chest != null && (((JSONObject) chest).get(existed)).equals("true")) {
      items.put("chest", new Chest((JSONObject) chest));
    }
  }

  private void prepareMirror(JSONObject wall) {
    Object mirror = wall.get("mirror");
    if (mirror != null && (((JSONObject) mirror).get(existed)).equals("true")) {
      items.put("mirror", new Mirror((JSONObject) mirror));
    }
  }

  private void preparePainting(JSONObject wall) {
    Object painting = wall.get("painting");
    if (painting != null && (((JSONObject) painting).get(existed)).equals("true")) {
      items.put("painting", new Painting((JSONObject) painting));
    }
  }

  private void prepareSeller(JSONObject wall) {
    Object seller = wall.get("seller");
    if (seller != null && (((JSONObject) seller).get(existed)).equals("true")) {
      items.put("seller", new Seller((JSONObject) seller));
    }
  }

  private void prepareSafe(JSONObject wall) {
    Object safe = wall.get("safe");
    if (safe != null && (((JSONObject) safe).get(existed)).equals("true")) {
      items.put("safe", new Safe((JSONObject) safe));
    }
  }

  private void prepareWindow(JSONObject wall) {
    Object window = wall.get("window");
    if (window != null && (((JSONObject) window).get(existed)).equals("true")) {
      items.put("window", new Window((JSONObject) window));
    }
  }

  private void prepareTable(JSONObject wall) {
    Object table = wall.get("table");
    if (table != null && (((JSONObject) table).get(existed)).equals("true")) {
      items.put("table", new Table((JSONObject) table));
    }
  }

  private void prepareGate(JSONObject wall) {
    Object gate = wall.get("gate");
    if (gate != null && (((JSONObject) gate).get(existed)).equals("true")) {
      items.put("gate", new Gate((JSONObject) gate));
    }
  }

  public String checkItemByLocation(String location) {
    for (Item item : this.items.values()) {
      if (item.compareTo(location) == 0) {
        return "This location has: " + item.getName();
      }
    }
    return "Nothing in this location";
  }

  public String getType(String location) {
    for (Item item : this.items.values()) {
      if (item.compareTo(location) == 0) {
        return item.getType();
      }
    }
    return "";
  }

  public Item getItem(String location) {
    for (Item item : this.items.values()) {
      if (item.compareTo(location) == 0) {
        return item;
      }
    }
    return new Space();
  }

  @Override
  public String toString() {
    return "Item Factory";
  }
}
