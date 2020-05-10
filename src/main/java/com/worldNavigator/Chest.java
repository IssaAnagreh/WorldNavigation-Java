package com.worldNavigator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chest extends Openable implements ContainerItems, Item, Checkable {
    public String name;
    public String location;
    private boolean taken;
    private ContentManager contents;

    public Chest(JSONObject chest) {
        this.name = (String) chest.get("name");
        if (chest.get("key") != null) {
            setKey(new Key((String) chest.get("key")));
        }
        initIs_locked(chest.get("is_locked").equals("true"));

        if (chest.get("existed").equals("true")) {
            this.contents = new ContentManager();
            this.contents.addItem(chest);
        }

        this.location = (String) chest.get("location");
    }

    public String getLocation() {
        return location;
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        if (this.taken) {
            System.out.println("This chest is empty now");
        } else {
            if (location.equals(this.location)) {
                if (!getIs_locked()) {
                    this.taken = true;
                    content = this.contents.getContents();
                } else {
                    System.out.println("You must use the key or find it for this chest");
                }
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
