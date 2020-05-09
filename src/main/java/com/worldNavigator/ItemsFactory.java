package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.*;

public class ItemsFactory {
    public HashMap<String, Object> items = new HashMap<String, Object>();

    public ItemsFactory(JSONObject wall) {
        Object door = wall.get("door");
        if (door != null) if ((((JSONObject) door).get("existed")).equals("true"))
            items.put("door", new Door((JSONObject) door));

        Object chest = wall.get("chest");
        if (chest != null)
            if ((((JSONObject) chest).get("existed")).equals("true"))
                items.put("chest", new Chest((JSONObject) chest));

        Object mirror = wall.get("mirror");
        if (mirror != null)
            if ((((JSONObject) mirror).get("existed")).equals("true"))
                items.put("mirror", new Mirror((JSONObject) mirror));

        Object painting = wall.get("painting");
        if (painting != null)
            if ((((JSONObject) painting).get("existed")).equals("true"))
                items.put("painting", new Painting((JSONObject) painting));

        Object seller = wall.get("seller");
        if (seller != null)
            if ((((JSONObject) seller).get("existed")).equals("true"))
                items.put("seller", new Seller((JSONObject) seller));
    }

    public String check_item_by_location(String location) {
        for (String item : this.items.keySet()) {
            Item location_item = (Item) this.items.get(item);
            if (location_item != null)
                if (location_item.getLocation().equals(location))
                    return "This location has: " + location_item.getName();
        }
        return "Nothing in this location";
    }

    public HashMap acquire_items(String location) {
        for (String item : this.items.keySet()) {
            Object location_item = this.items.get(item);
            if (location_item != null)
                if (((Item) location_item).getLocation().equals(location)) {
                    if (location_item instanceof Checkable) {
                        HashMap acquire_items = ((ContainerItems) location_item).check_content(location);
                        if (acquire_items.size() > 0) System.out.println("New items are acquired " + acquire_items);
                        return acquire_items;
                    }
                }
        }
        return new HashMap<String, Object>();
    }


    public Object getItem(String location) {
        for (String item : this.items.keySet()) {
            Object location_item = this.items.get(item);
            if (location_item != null)
                if (((Item) location_item).getLocation().equals(location)) {
                    return location_item;
                }
        }
        System.out.println("No such element in this location");
        return null;
    }
}
