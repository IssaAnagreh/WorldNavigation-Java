package com.worldNavigator;

import java.util.HashMap;

public class Openable {
    private Key key;
    private Boolean is_locked;

    public Boolean getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(Boolean is_locked) {
        this.is_locked = is_locked;
    }

    public String getKey() {
        return key.toString();
    }

    public void setKey(Key key) {
        this.key = key;
    }

}
