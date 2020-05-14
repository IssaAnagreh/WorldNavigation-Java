package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Mirror implements Item, Checkable {
    private ContentManager contents;
    private final String LOCATION;
    private final String NAME = "Mirror";
    private boolean isTaken;

    public Mirror(JSONObject mirror) {
        if (mirror.get("existed").equals("true")) {
            this.contents = new ContentManager();
            this.contents.addItem(mirror);
        }
        this.LOCATION = (String) mirror.get("location");
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        if (this.isTaken) {
            System.out.println("This mirror is empty now");
        } else {
            if (location.equals(this.LOCATION)) {
                this.isTaken = true;
                content = this.contents.getContents();
            }
        }
        return content;
    }

    public String getLocation() {
        return this.LOCATION;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "mirror";
    }

    @Override
    public String getDetails() {
        return "You see a silhouette of you because of a " + this.NAME + " in " + this.LOCATION;
    }

    @Override
    public String toString() {
        return "Mirror, Location: " + this.LOCATION;
    }
}
