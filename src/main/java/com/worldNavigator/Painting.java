package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Painting implements ContainerItems, Item, Checkable {
    private ContentManager contents;
    public String location;
    public String name = "Painting";
    private boolean taken;

    public Painting(JSONObject painting) {
        if (painting.get("existed").equals("true")) {
            this.contents = new ContentManager();
            this.contents.addItem(painting);
        }
        this.location = (String) painting.get("location");
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        if (this.taken) {
            System.out.println("This painting is empty now");
        } else {
            if (location.equals(this.location)) {
                this.taken = true;
                content = this.contents.getContents();
            }
        }
        return content;
    }

    @Override
    public String getLocation() {
        return location;
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
        return "Painting, Location: " + this.location;
    }
}
