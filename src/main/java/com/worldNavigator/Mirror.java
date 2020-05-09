package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mirror implements ContainerItems, Item, Checkable {
    public List<Key> keys = new ArrayList<Key>();
    private long golds;
    private JSONObject content;
    public String location;
    public String name = "Mirror";
    private boolean taken;

    public Mirror(JSONObject mirror) {
        this.content = (JSONObject) mirror.get("content");
        if (mirror.get("existed").equals("true")) {
            if (this.content.get("keys") != null) {
                JSONArray keys_names = (JSONArray) content.get("keys");
                if (keys_names != null) keys_names.forEach(emp -> keys.add(new Key(emp.toString())));
            }
            if (content.get("golds") != null) this.golds = (long) content.get("golds");
            this.location = (String) mirror.get("location");
        }
    }

    public Mirror() {
    }

    public HashMap check_content(String location) {
        HashMap<String, Object> content = new HashMap<String, Object>();
        if (this.taken) {
            System.out.println("This chest is empty now");
        } else {
            if (location.equals(this.location)) {
                content.put("keys", this.keys);
                content.put("golds", this.golds);
                this.taken = true;
            } else {
                System.out.println("You must be in the same location of this chest");
            }
        }
        return content;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDetails() {
        return "You see a silhouette of you because of a " + name + " in " + location;
    }

    @Override
    public String toString() {
        return "Mirror, Location: " + this.location;
    }
}
