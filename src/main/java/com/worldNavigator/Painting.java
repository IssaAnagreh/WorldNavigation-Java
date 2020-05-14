package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Painting implements Item, Checkable {
    private ContentManager contents;
    public final String LOCATION;
    public final String NAME = "Painting";
    private boolean isTaken;

    public Painting(JSONObject painting) {
        if (painting.get("existed").equals("true")) {
            this.contents = new ContentManager();
            this.contents.addItem(painting);
        }
        this.LOCATION = (String) painting.get("location");
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        if (this.isTaken) {
            System.out.println("This painting is empty now");
        } else {
            if (location.equals(this.LOCATION)) {
                this.isTaken = true;
                content = this.contents.getContents();
            }
        }
        return content;
    }

    @Override
    public String getLocation() {
        return this.LOCATION;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "painting";
    }

    @Override
    public String getDetails() {
        return this.NAME + " in " + this.LOCATION;
    }

    @Override
    public String toString() {
        return "Painting, Location: " + this.LOCATION;
    }
}
