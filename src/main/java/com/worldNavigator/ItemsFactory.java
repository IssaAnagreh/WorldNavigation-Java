package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.*;

public class ItemsFactory {
    public HashMap<String, Object> items = new HashMap<String, Object>();
    private Door door;
    public Chest chest;
    public Mirror mirror;
    public Painting painting;
    public Seller seller;

    public ItemsFactory(JSONObject wall) {
        if ((((JSONObject) wall.get("door")).get("existed")).equals("true"))
            door = new Door((JSONObject) wall.get("door"));

        if ((((JSONObject) wall.get("chest")).get("existed")).equals("true"))
            chest = new Chest((JSONObject) wall.get("chest"));

        if ((((JSONObject) wall.get("mirror")).get("existed")).equals("true"))
            mirror = new Mirror((JSONObject) wall.get("mirror"));

        if ((((JSONObject) wall.get("painting")).get("existed")).equals("true"))
            painting = new Painting((JSONObject) wall.get("painting"));

        if ((((JSONObject) wall.get("seller")).get("existed")).equals("true"))
            seller = new Seller((JSONObject) wall.get("seller"));

        items.put("door", door);
        items.put("chest", chest);
        items.put("mirror", mirror);
        items.put("painting", painting);
        items.put("seller", seller);
    }

    public String check_item_by_location(String location) {
        for (String item : this.items.keySet()) {
            ContainerItems location_item = (ContainerItems) this.items.get(item);
            if (location_item != null) if (location_item.getLocation().equals(location))
                return "This location has: " + location_item.getName();
        }
        return "Nothing in this location";
    }

    public HashMap acquire_items(String location) {
        for (String item : this.items.keySet()) {
            Object location_item = this.items.get(item);
            if (location_item != null) if (((ContainerItems) location_item).getLocation().equals(location)) {
                if (!((ContainerItems) location_item).getName().equals("Seller")){
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
            if (location_item != null) if (((ContainerItems) location_item).getLocation().equals(location)) {
                return location_item;
            }
        }
        System.out.println("No such element in this location");
        return null;
    }
}
