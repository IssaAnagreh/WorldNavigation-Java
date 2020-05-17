package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Uncheckable implements CheckBehavior {

    public HashMap check_content(String location) {
        HashMap content = new HashMap<String, Object>();
        return content;
    }

    public HashMap<String, Object> acquire_contents(String location) {
        HashMap<String, Object> acquired_items = this.check_content(location);
        return new HashMap();
    }

    @Override
    public String toString() {
        return "Uncheckable";
    }
}
