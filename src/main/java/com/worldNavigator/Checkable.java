package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

public abstract class Checkable implements CheckBehavior {
  protected ContentManager contents;
  protected final String LOCATION;

  public Checkable(JSONObject item, String location) {
    this.contents = new ContentManager();
    this.contents.addItem(item);
    this.LOCATION = location;
  }

  public void acquire_contents(String location, PlayerModel playerModel) {
    HashMap<String, Object> acquired_contents = this.check_content(location, playerModel);
    if (acquired_contents.isEmpty()) {
      return;
    } else {
      if (acquired_contents.get("keys") != null) {
        ((List) acquired_contents.get("keys"))
            .forEach(emp -> ((List) playerModel.contents.get("keys")).add(emp));
      }
      if (acquired_contents.get("golds") != null) {
        playerModel.contents.put(
            "golds",
            ((int) playerModel.contents.get("golds")) + ((int) acquired_contents.get("golds")));
      }
      if (acquired_contents.get("flashLights") != null) {
        playerModel.contents.put(
            "flashLights",
            ((int) playerModel.contents.get("flashLights"))
                + ((int) acquired_contents.get("flashLights")));
      }
      if (acquired_contents.get("masterKeys") != null) {
        playerModel.contents.put(
            "masterKeys",
            ((int) playerModel.contents.get("masterKeys"))
                + ((int) acquired_contents.get("masterKeys")));
      }
      playerModel.notify_player("Contents acquired " + acquired_contents);
    }
  }
}
