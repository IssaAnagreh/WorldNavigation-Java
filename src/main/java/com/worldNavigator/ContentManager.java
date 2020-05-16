package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContentManager {
    private HashMap<String, Object> contents = new HashMap<String, Object>();

    public void addItem(JSONObject item) {
        JSONObject content = (JSONObject) item.get("content");
        if (item.get("existed").equals("true")) {
            if (content.get("keys") != null) {
                JSONArray keys_names = (JSONArray) content.get("keys");
                List<Key> keys = new ArrayList<Key>();
                if (keys_names != null) {
                    keys_names.forEach(emp -> keys.add(new Key(emp.toString())));
                    this.contents.put("keys", keys);
                }
            }
            if (content.get("flashLight") != null) {
                long flashLight;
                flashLight = (long) content.get("flashLight");
                this.contents.put("flashLight", flashLight);
            }
            if (content.get("golds") != null) {
                long golds;
                golds = (long) content.get("golds");
                this.contents.put("golds", golds);
            }
        }
    }

    public void addSellingItem(JSONObject seller) {
        if (seller.get("existed").equals("true")) {
            this.contents = (HashMap) seller.get("content");
            for (String contentKey: contents.keySet()) {
                JSONArray temp = (JSONArray) contents.get(contentKey);
                if (contentKey.equals("keys")) {
                    if (temp != null) temp.forEach(emp -> {
                        HashMap<String, Object> key = (HashMap) emp;
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

    public long getGolds() {
        return (long) this.contents.get("golds");
    }

    public long getFlashLight() {
        return (long) this.contents.get("flashLight");
    }

    public List getKeys() {
        return (List) this.contents.get("keys");
    }
}
