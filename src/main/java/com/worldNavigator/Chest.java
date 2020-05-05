package com.worldNavigator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Chest extends Openable implements ItemsContainer {
    public String name;
    public List<Key> keys = new ArrayList<Key>();
    private long flashLight;
    private long golds;
    public String location;
    private boolean taken;

    public Chest(JSONObject chest) {
        this.name = (String) chest.get("name");
        if (chest.get("key") != null) {
            setKey(new Key((String) chest.get("key")));
        }
        initIs_locked(chest.get("is_locked").equals("true"));
        JSONObject content = (JSONObject) chest.get("content");
        if (chest.get("existed").equals("true")) {
            if (content.get("keys") != null) {
                JSONArray keys_names = (JSONArray) content.get("keys");
                if (keys_names != null) keys_names.forEach(emp -> keys.add(new Key(emp.toString())));
            }
            if (content.get("flashLight") != null) this.flashLight = (long) content.get("flashLight");
            if (content.get("golds") != null) this.golds = (long) content.get("golds");
        }
        this.location = (String) chest.get("location");
    }

    public long getGolds() {
        return golds;
    }

    public String getLocation() {
        return location;
    }

    public HashMap check_content(String location) {
        HashMap<String, Object> content = new HashMap<String, Object>();
        if (this.taken) {
            System.out.println("This chest is empty now");
        } else {
            if (location.equals(this.location)) {
                if (!getIs_locked()) {
                    content.put("keys", this.keys);
                    content.put("flashLight", this.flashLight);
                    content.put("golds", this.golds);
                    this.taken = true;
                } else {
                    System.out.println("You must use the key or find it for this chest");
                }
            } else {
                System.out.println("You must be in the same location of this chest");
            }
        }
        return content;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDetails() {
        return name + " in " + location;
    }

    @Override
    public String toString() {
        return getIs_locked() ? "LOCKED Chest: " + name + ", Location: " + this.location : "UNLOCKED Chest: " + name + ", Location: " + this.location;
    }
}
