package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

public class Mirror implements ItemsContainer {
    private JSONArray keys;
    private int golds;
    private JSONObject content;
    public String location;
    public static Key key;
    public String name;
    private boolean taken;

    public Mirror(JSONObject mirror) {
        this.content = (JSONObject) mirror.get("content");
        if (mirror.get("existed").equals("true")) {
            if (this.content.get("keys") != null) this.keys = (JSONArray) this.content.get("keys");
            if (this.content.get("golds") != null) this.golds = (int) this.content.get("golds");
            this.location = (String) mirror.get("location");
            this.name = "Mirror";
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
                List<Key> temp_keys = this.keys;
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
    public String toString() {
        return "Mirror, Location: " + this.location;
    }
}
