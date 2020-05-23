package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Wall {
  public final String NAME;
  public ItemsFactory itemsFactory;
  public Map<String, Item> items = new HashMap<>();

  public Wall(String name, JSONObject wall) {
    this.NAME = name;
    generateWall(wall);
  }

  private void generateWall(JSONObject wall) {
    this.itemsFactory = new ItemsFactory(wall);
    this.items = this.itemsFactory.items;
  }

  public String checkItems() {
    return this.items.isEmpty() ? "Nothing to look at" : this.items.values().toString();
  }

  @Override
  public String toString() {
    return "Wall name: " + this.NAME + ", items: " + checkItems();
  }
}
