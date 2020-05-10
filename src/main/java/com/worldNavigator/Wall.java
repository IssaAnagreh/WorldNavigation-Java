package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Wall {
    public String name;
    public ItemsFactory itemsFactory;
    public HashMap<String, Object> items = new HashMap<String, Object>();

    public Wall(String name, JSONObject wall) {
        this.name = name;
        this.itemsFactory = new ItemsFactory(wall);
        this.items = this.itemsFactory.items;
    }

    public String check_items() {
        List<String> temp_items = new ArrayList();
        for (Object item : this.items.keySet()) {
            Item location_item = (Item) this.items.get(item);
            if (this.items.get(item) != null) temp_items.add(location_item.getDetails());
        }
        return temp_items.size() > 0 ? temp_items.toString() : "Nothing to look at";
    }

    @Override
    public String toString() {
        return "Wall name: " + this.name + ", items: " + check_items();
    }
}
