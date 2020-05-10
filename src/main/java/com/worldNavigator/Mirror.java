package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mirror implements ContainerItems, Item, Checkable {
    private ContentManager contents;
    public String location;
    public String name = "Mirror";
    private boolean taken;

    public Mirror(JSONObject mirror) {
        if (mirror.get("existed").equals("true")) {
            this.contents = new ContentManager();
            this.contents.addItem(mirror);
        }
        this.location = (String) mirror.get("location");
    }

    public Mirror() {
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        if (this.taken) {
            System.out.println("This mirror is empty now");
        } else {
            if (location.equals(this.location)) {
                this.taken = true;
                content = this.contents.getContents();
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
