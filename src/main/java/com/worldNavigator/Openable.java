package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.List;

public class Openable implements UseKeyBehavior {
    private Key key;
    private Boolean isLocked;
    private final String NAME;

    public Openable(JSONObject item, String name) {
        setKey(new Key((String) item.get("key")));
        initIs_locked(item.get("is_locked").equals("true"));
        this.NAME = name;
    }

    public Boolean getIs_locked() {
        return isLocked;
    }

    public void initIs_locked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setIs_locked(Boolean isLocked) {
        this.isLocked = !this.isLocked ? false : isLocked;
    }

    public String getKey() {
        return key.toString();
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String useKey(List<Key> keys) {
        String print = "";
        if (this.getIs_locked()) {
            boolean locked = true;
            for (Key keyItem : keys) {
                locked = !locked ? false : !keyItem.unlock(this);
                this.setIs_locked(locked);
            }
            if (locked) {
                print = "Look for a suitable key";
            } else {
                print = this.NAME + " is open now";
            }
        } else {
            print = this.NAME + " is already open";
        }
        return print;
    }
}
