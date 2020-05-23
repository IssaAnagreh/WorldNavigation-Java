package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentManager {
  private Map<String, Object> contents = new HashMap<>();

  public void manageItem(JSONObject item) {
    JSONObject content = (JSONObject) item.get("content");
    if (item.get("existed").equals("true")) {
      String keysString = "keys";
      this.contents.put(keysString, new ArrayList<Key>());
      if (content.get(keysString) != null) {
        JSONArray keys_names = (JSONArray) content.get(keysString);
        List<Key> keys = new ArrayList<>();
        if (keys_names != null) {
          keys_names.forEach(emp -> keys.add(new Key(emp.toString())));
          this.contents.put(keysString, keys);
        }
      }

      String flashLightString = "flashLights";
      this.contents.put(flashLightString, 0);
      if (content.get(flashLightString) != null) {
        int flashLight;
        flashLight = Integer.parseInt(content.get(flashLightString).toString());
        this.contents.put(flashLightString, flashLight);
      }

      String goldsString = "golds";
      this.contents.put(goldsString, 0);
      if (content.get(goldsString) != null) {
        int golds;
        golds = Integer.parseInt(content.get(goldsString).toString());
        this.contents.put(goldsString, golds);
      }

      String masterKeyString = "masterKeys";
      this.contents.put(masterKeyString, 0);
      if (content.get(masterKeyString) != null) {
        int masterKeys_number;
        masterKeys_number = Integer.parseInt(content.get(masterKeyString).toString());
        this.contents.put(masterKeyString, masterKeys_number);
      }
    }
  }

  public void manageSellerItem(JSONObject seller) {
    if (seller.get("existed").equals("true")) {
      this.contents = (HashMap) seller.get("content");
      for (String contentKey : contents.keySet()) {
        if (contentKey.equals("keys")) {
          JSONArray temp = (JSONArray) contents.get(contentKey);
          if (temp != null)
            temp.forEach(
                emp -> {
                  HashMap<String, Object> key = (HashMap<String, Object>) emp;
                  key.replace("name", new Key(key.get("name").toString()));
                  key.replace("cost", new Key(key.get("cost").toString()));
                });
        }
      }
    }
  }

  public void managePlayer(Map<String, Object> player) {
    String goldsString = "golds";
    if (player.get(goldsString) == null) {
      this.contents.put(goldsString, 0);
    } else {
      this.contents.put(goldsString, Integer.parseInt(player.get(goldsString).toString()));
    }

    String flashLightString = "flashLights";
    if (player.get(flashLightString) == null) {
      this.contents.put(flashLightString, 0);
    } else {
      this.contents.put(flashLightString, Integer.parseInt(player.get(flashLightString).toString()));
    }

    String masterKeyString = "masterKeys";
    if (player.get(masterKeyString) == null) {
      this.contents.put(masterKeyString, 0);
    } else {
      this.contents.put(masterKeyString, Integer.parseInt(player.get(masterKeyString).toString()));
    }
    this.contents.put("keys", new ArrayList<Key>());
  }

  public Map<String, Object> getContents() {
    return this.contents;
  }

  @Override
  public String toString() {
    return this.getContents().toString();
  }
}
