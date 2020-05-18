package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Locked_Checkable implements CheckBehavior {
  private ContentManager contents;
  private boolean isTaken;
  private final String LOCATION;
  private UseKeyBehavior useKeyBehavior;

  public Locked_Checkable(JSONObject item, String location, UseKeyBehavior useKeyBehavior) {
    this.contents = new ContentManager();
    this.contents.addItem(item);
    this.LOCATION = location;
    this.useKeyBehavior = useKeyBehavior;
  }

  public HashMap check_content(String location, PlayerModel playerModel) {
    HashMap content = new HashMap<String, Object>();
    if (this.isTaken) {
      playerModel.notify_player("This chest is empty now");
    } else {
      if (this.compareTo(location) == 0) {
        if (this.useKeyBehavior.getIs_locked() != null && this.useKeyBehavior.getIs_locked()) {
          playerModel.notify_player("You must use the key or find it for this chest");
        } else {
          this.isTaken = true;
          content = this.contents.getContents();
        }
      }
    }
    return content;
  }

  public HashMap<String, Object> acquire_contents(String location, PlayerModel playerModel) {
    HashMap<String, Object> acquired_items = this.check_content(location, playerModel);
    if (acquired_items.size() > 0) {
      return acquired_items;
    } else {
      return new HashMap();
    }
  }

  @Override
  public String toString() {
    return "Locked_Checkable";
  }

  public int compareTo(String location) {
    return this.LOCATION.compareTo(location);
  }
}
