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
            System.out.println("Object is open now");
            return this.name.equals(openable.getKey());
        }
        System.out.println("Keys are used for locked chests and doors");
        return false;
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
