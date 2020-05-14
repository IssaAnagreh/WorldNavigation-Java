package com.worldNavigator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chest extends Openable implements Item, Checkable {
    private final String NAME;
    private final String LOCATION;
    private boolean isTaken;
    private ContentManager contents;

    public Chest(JSONObject chest) {
        this.NAME = (String) chest.get("name");
        if (chest.get("key") != null) {
            setKey(new Key((String) chest.get("key")));
        }
        initIs_locked(chest.get("is_locked").equals("true"));

        if (chest.get("existed").equals("true")) {
            this.contents = new ContentManager();
            this.contents.addItem(chest);
        }

        this.LOCATION = (String) chest.get("location");
    }

    public String getLocation() {
        return this.LOCATION;
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        if (this.isTaken) {
            System.out.println("This chest is empty now");
        } else {
            if (location.equals(this.LOCATION)) {
                if (!getIs_locked()) {
                    this.isTaken = true;
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
        return this.NAME;
    }

    public String getType() {
        return "chest";
    }

    @Override
    public String getDetails() {
        return this.NAME + " in " + this.LOCATION;
    }

    @Override
    public String toString() {
        return getIs_locked() ? "LOCKED Chest: " + this.NAME + ", Location: " + this.LOCATION : "UNLOCKED Chest: " + this.NAME + ", Location: " + this.LOCATION;
    }
}
