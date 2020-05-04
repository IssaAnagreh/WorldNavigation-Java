package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Painting implements ItemsContainer {
    public List<Key> keys = new ArrayList<Key>();
    private int golds;
    private JSONObject content;
    public String location;
    public String name;

    public Painting(JSONObject painting) {
        this.content = (JSONObject) painting.get("content");
        if (painting.get("existed") == "true") {
            if (this.content.get("keys") != null) {
                JSONArray keys_names = (JSONArray) content.get("keys");
                System.out.println("keys_names: "+keys_names);
                if (keys_names != null) keys_names.forEach(emp -> keys.add(new Key(emp.toString())));
            }
            if (this.content.get("golds") != null) this.golds = (int) this.content.get("golds");
            this.location = (String) painting.get("location");
        }
    }

    public String check() {
        if (content != null) {
            return "this painting has: " + content ;
        } else {
            return "this painting has nothing";
        }
    }

    public HashMap check_content(String location) {
        HashMap<String, Object> content = new HashMap<String, Object>();
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
