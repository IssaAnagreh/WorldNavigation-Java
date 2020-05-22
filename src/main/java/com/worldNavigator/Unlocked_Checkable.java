package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Unlocked_Checkable extends Checkable implements CheckBehavior {
  private boolean isTaken;

  public Unlocked_Checkable(JSONObject item, String location) {
    super(item, location);

  }

  public HashMap check_content(String location, PlayerModel playerModel) {
    HashMap content = new HashMap<String, Object>();
    if (this.isTaken) {
      playerModel.notify_player("Empty!");
    } else {
      if (location.equals(super.LOCATION)) {
        this.isTaken = true;
        System.out.println("super.contents.getContents() "+super.contents.getContents());
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
