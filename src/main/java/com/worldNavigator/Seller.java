package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Seller implements ItemsContainer {
    public String location;
    public String name;

    public Seller(JSONObject seller) {
        if (seller.get("existed").equals("true")) {
            this.location = (String) seller.get("location");
        }
    }

    public String getLocation() {
        return location;
    }

    public HashMap check_content(String location) {
        HashMap<String, Object> content = new HashMap<String, Object>();
        return content;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Seller, Location: " + this.location;
    }
}
