package com.worldNavigator;

import java.util.HashMap;
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

    public HashMap applyAcquire(String location) {
        return checkBehavior.acquire_contents(location);
    }

    public String applyUseKey(List<Key> keys) {
        return useKeyBehavior.useKey(keys);
    }

    @Override
    public abstract String toString();

    public abstract int compareTo(String location);
}
