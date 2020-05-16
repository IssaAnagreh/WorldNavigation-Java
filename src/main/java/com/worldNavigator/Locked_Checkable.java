package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Locked_Checkable implements CheckBehavior {
    private ContentManager contents;
    private boolean isTaken;
    private final String LOCATION;
    private UseKeyBehavior useKeyBehavior;

    public Locked_Checkable(JSONObject item, String location, UseKeyBehavior useKeyBehavior) {
        this.contents = new ContentManager();
        this.contents.addItem(item);
        this.LOCATION = location;
        this.useKeyBehavior = useKeyBehavior;
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        if (this.isTaken) {
            System.out.println("This chest is empty now");
        } else {
            if (location.equals(this.LOCATION)) {
                if (!this.useKeyBehavior.getIs_locked()) {
                    this.isTaken = true;
                    content = this.contents.getContents();
                } else {
                    System.out.println("You must use the key or find it for this chest");
                }
            }
        }
        return content;
    }

    public HashMap<String, Object> acquire_items(String location) {
        HashMap<String, Object> acquired_items = this.check_content(location);
        if (acquired_items.size() > 0) {
            return acquired_items;
        } else {
            return new HashMap();
        }
    }

    @Override
    public String toString() {
        return "Locked_Checkable";
    }
}
