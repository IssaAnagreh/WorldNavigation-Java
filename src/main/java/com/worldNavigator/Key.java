package com.worldNavigator;

import java.util.Iterator;

public class Key implements KeyChecker {
    private final String NAME;

    public Key(String name) {
        this.NAME = name;
    }

    public String getName() {
        return this.NAME;
    }

    public Boolean unlock(Openable openable){
        if (openable != null) if (this.NAME.equals(openable.getKey())) {
            return false;
        } else {
            return true;
        }
        return true;
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
