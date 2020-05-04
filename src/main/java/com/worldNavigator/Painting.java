package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Painting implements ItemsContainer {
    private JSONArray keys;
    private int golds;
    private JSONObject content;
    public String location;
    public static Key key;
    private String keyName;
    public String name;

    public Painting(JSONObject painting) {
        this.content = (JSONObject) painting.get("content");
        if (painting.get("existed") == "true") {
            this.keys = (JSONArray) this.content.get("keys");
            this.keyName = (String) painting.get("key");
            if (this.keyName != null) {
                key = new Key(this.keyName);
            }
            this.golds = (int) this.content.get("golds");
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
