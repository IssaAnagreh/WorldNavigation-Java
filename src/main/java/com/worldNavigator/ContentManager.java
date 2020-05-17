package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContentManager {
    private HashMap<String, Object> contents = new HashMap();

    public void addItem(JSONObject item) {
        JSONObject content = (JSONObject) item.get("content");
        if (item.get("existed").equals("true")) {
            if (content.get("keys") != null) {
                JSONArray keys_names = (JSONArray) content.get("keys");
                List<Key> keys = new ArrayList();
                if (keys_names != null) {
                    keys_names.forEach(emp -> keys.add(new Key(emp.toString())));
                    this.contents.put("keys", keys);
                }
            }

            String flashLightString = "flashLight";
            if (content.get(flashLightString) != null) {
                long flashLight;
                flashLight = (long) content.get(flashLightString);
                this.contents.put(flashLightString, flashLight);
            }

            String goldsString = "golds";
            if (content.get(goldsString) != null) {
                long golds;
                golds = (long) content.get(goldsString);
                this.contents.put(goldsString, golds);
            }
        }
    }

    public void addSellerItem(JSONObject seller) {
        if (seller.get("existed").equals("true")) {
            this.contents = (HashMap) seller.get("content");
            for (String contentKey: contents.keySet()) {
                if (contentKey.equals("keys")) {
                    JSONArray temp = (JSONArray) contents.get(contentKey);
                    if (temp != null) temp.forEach(emp -> {
                        HashMap<String, Object> key = (HashMap<String, Object>) emp;
                        key.replace("name", new Key(key.get("name").toString()));
                        key.replace("cost", new Key(key.get("cost").toString()));
                    });
                }
            }
        }
    }

    public HashMap getContents() {
        return this.contents;
    }

    @Override
    public String toString() {
        return this.getContents().toString();
    }
}
