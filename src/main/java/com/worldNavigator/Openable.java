package com.worldNavigator;

import java.util.HashMap;

public class Openable {
    private Key key;
    private Boolean isLocked;

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

}
