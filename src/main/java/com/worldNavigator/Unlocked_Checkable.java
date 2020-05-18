package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Unlocked_Checkable implements CheckBehavior {
    private ContentManager contents;
    private boolean isTaken;
    private final String LOCATION;

    public Unlocked_Checkable(JSONObject item, String location) {
        this.contents = new ContentManager();
        this.contents.addItem(item);
        this.LOCATION = location;
    }

    public HashMap check_content(String location, PlayerModel playerModel) {
        HashMap content = new HashMap<String, Object>();
        if (this.isTaken) {
            playerModel.notify_player("Nothing to acquire");
        } else {
            if (location.equals(this.LOCATION)) {
                this.isTaken = true;
                content = this.contents.getContents();
            }
        }
        return content;
    }

    public HashMap<String, Object> acquire_contents(String location, PlayerModel playerModel) {
        HashMap<String, Object> acquired_items = this.check_content(location, playerModel);
        if (acquired_items.size() > 0) {
            return acquired_items;
        } else {
            return new HashMap();
        }
    }

    @Override
    public String toString() {
        return "Unlocked Checkable";
    }
}
