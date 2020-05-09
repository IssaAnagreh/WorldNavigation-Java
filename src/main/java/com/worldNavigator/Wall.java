package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Wall extends ItemsFactory {
    public String name;
    public HashMap<String, Object> items;

    public Wall(String name, JSONObject wall) {
        super(wall);
        this.name = name;
        this.items = super.items;
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
