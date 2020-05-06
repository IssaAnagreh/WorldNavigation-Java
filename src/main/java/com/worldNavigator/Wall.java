package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Wall {
    public HashMap<String, Object> items = new HashMap<String, Object>();
    public String name;
    private Door door;
    public Chest chest;
    public Mirror mirror;
    public Painting painting;
    public Seller seller;

    public Wall(String name, JSONObject wall) {
        this.name = name;

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
            ItemsContainer location_item = (ItemsContainer) this.items.get(item);
            if (location_item != null) if (location_item.getLocation().equals(location))
                return "This location has: " + location_item.getName();
        }
        return "Nothing in this location";
    }

    public String check_ite
    qms_location() {
        List<String> temp_items = new ArrayList();
        for (Object item : this.items.keySet()) {
            ItemsContainer location_item = (ItemsContainer) this.items.get(item);
            if (this.items.get(item) != null) temp_items.add(location_item.getDetails());
        }
        return temp_items.size() > 0 ? temp_items.toString() : "Nothing to look at";
    }

    public HashMap acquire_items(String location) {
        for (String item : this.items.keySet()) {
            Object location_item = this.items.get(item);
            if (location_item != null) if (((ItemsContainer) location_item).getLocation().equals(location)) {
                if (!((ItemsContainer) location_item).getName().equals("Seller")){
                    HashMap acquire_items = ((ItemsContainer) location_item).check_content(location);
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
            if (location_item != null) if (((ItemsContainer) location_item).getLocation().equals(location)) {
                return location_item;
            }
        }
        System.out.println("No such element in this location");
        return null;
    }

    @Override
    public String toString() {
        return "Wall name: " + this.name + ", items: " + check_items_location();
    }
}
