package com.worldNavigator;

import java.util.Iterator;

public class Key implements KeyChecker {
    private final String name;

    public Key(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean unlock(Openable openable){
        if (openable != null) if (this.name.equals(openable.getKey())) {
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
