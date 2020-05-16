package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.*;

public class ItemsFactory {
    public HashMap<String, Item> items = new HashMap<String, Item>();

    public ItemsFactory(JSONObject wall) {
        Object door = wall.get("door");
        if (door != null) {
            if ((((JSONObject) door).get("existed")).equals("true")) {
                items.put("door", new Door((JSONObject) door));
            }
        }

        Object chest = wall.get("chest");
        if (chest != null) {
            if ((((JSONObject) chest).get("existed")).equals("true")) {
                items.put("chest", new Chest((JSONObject) chest));
            }
        }

        Object mirror = wall.get("mirror");
        if (mirror != null) {
            if ((((JSONObject) mirror).get("existed")).equals("true")) {
                items.put("mirror", new Mirror((JSONObject) mirror));
            }
        }

        Object painting = wall.get("painting");
        if (painting != null) {
            if ((((JSONObject) painting).get("existed")).equals("true")) {
                items.put("painting", new Painting((JSONObject) painting));
            }
        }

        Object seller = wall.get("seller");
        if (seller != null) {
            if ((((JSONObject) seller).get("existed")).equals("true")) {
                items.put("seller", new Seller((JSONObject) seller));
            }
        }
    }

    public String check_item_by_location(String location) {
        for (String item : this.items.keySet()) {
            Item location_itemInterface = this.items.get(item);
            if (location_itemInterface != null) {
                if (location_itemInterface.getLocation().equals(location)) {
                    return "This location has: " + location_itemInterface.getName();
                }
            }
        }
        return "Nothing in this location";
    }

    public String getType(String location) {
        ArrayList output = new ArrayList();
        for (String item : this.items.keySet()) {
            Item location_itemInterface = this.items.get(item);
            if (location_itemInterface != null)
                if (location_itemInterface.getLocation().equals(location))
                    return location_itemInterface.getType();
        }
        return "";
    }

    public Item getItem(String location) {
        for (String item : this.items.keySet()) {
            Item location_item = this.items.get(item);
            if (location_item != null) {
                if ((location_item).getLocation().equals(location)) {
                    return location_item;
                }
            }
        }
        return null;
    }
}
