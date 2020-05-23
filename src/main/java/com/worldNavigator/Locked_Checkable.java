package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Locked_Checkable extends Checkable implements CheckBehavior {
  private boolean isTaken;
  private UseKeyBehavior useKeyBehavior;

  public Locked_Checkable(JSONObject item, String location, UseKeyBehavior useKeyBehavior) {
    super(item, location);
    this.useKeyBehavior = useKeyBehavior;
  }

  public Map<String, Object> check_content(String location, PlayerModel playerModel) {
    Map<String, Object> content = new HashMap<>();
    if (this.isTaken) {
      playerModel.notify_player("Empty!");
    } else {
      if (this.compareTo(location) == 0) {
        if (this.useKeyBehavior.getIs_locked() != null && this.useKeyBehavior.getIs_locked()) {
          playerModel.notify_player("You must use the key or find it for this object");
        } else {
          this.isTaken = true;
          content = super.contents.getContents();
        }
      }
    }
    return content;
  }

  @Override
  public String toString() {
    return "Locked_Checkable";
  }

  public int compareTo(String location) {
    return super.LOCATION.compareTo(location);
  }
}
