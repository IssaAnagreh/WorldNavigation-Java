package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Wall {
    public final String NAME;
    public ItemsFactory itemsFactory;
    public HashMap<String, Item> items = new HashMap<String, Item>();

    public Wall(String name, JSONObject wall) {
        this.NAME = name;
        generateWall(wall);
    }

    private void generateWall(JSONObject wall) {
        this.itemsFactory = new ItemsFactory(wall);
        this.items = this.itemsFactory.items;
    }

    public String check_items() {
        List<String> temp_items = new ArrayList();
        for (Object item : this.items.keySet()) {
            Item location_itemInterface = (Item) this.items.get(item);
            if (this.items.get(item) != null) temp_items.add(location_itemInterface.getDetails());
        }
        return temp_items.size() > 0 ? temp_items.toString() : "Nothing to look at";
    }

    @Override
    public String toString() {
        return "Wall name: " + this.NAME + ", items: " + check_items();
    }
}
