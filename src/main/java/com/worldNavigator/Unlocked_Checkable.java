package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Unlocked_Checkable extends Checkable implements CheckBehavior {
  private boolean isTaken;

  public Unlocked_Checkable(JSONObject item, String location) {
    super(item, location);

  }

  public Map<String, Object> check_content(String location, PlayerModel playerModel) {
    Map<String, Object> content = new HashMap<>();
    if (this.isTaken) {
      playerModel.notify_player("Empty!");
    } else {
      if (location.equals(super.LOCATION)) {
        this.isTaken = true;
        content = super.contents.getContents();
      }
    }
    return content;
  }

  @Override
  public String toString() {
    return "Unlocked Checkable";
  }
}
