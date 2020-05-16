package com.worldNavigator;

import java.util.List;

public abstract class Item {
    CheckBehavior checkBehavior;
    UseKeyBehavior useKeyBehavior;

    public void setCheckBehavior (CheckBehavior cb) {
        this.checkBehavior = cb;
    }

    public void setUseKeyBehavior (UseKeyBehavior ukb) {
        this.useKeyBehavior = ukb;
    }

    public abstract String getLocation();

    public abstract String getName();

    public abstract String getType();

    public String getDetails() {
        return "Implement getDetails in Item Class";
    }

    public void applyCheck(String location) {
        checkBehavior.check_content(location);
    }

    public void applyUseKey(List<Key> keys) {
        useKeyBehavior.useKey(keys);
    }
}
