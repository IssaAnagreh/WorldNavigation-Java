package com.worldNavigator;

import org.json.simple.JSONObject;

public class Chest extends Item {
  private final String NAME;
  private final String LOCATION;

  public Chest(JSONObject chest) {
    this.NAME = (String) chest.get("name");
    this.LOCATION = (String) chest.get("location");

    if (chest.get("key") == null) {
      if (chest.get("existed").equals("true")) {
        super.setCheckBehavior(new Unlocked_Checkable(chest, this.LOCATION));
      }
    } else {
      super.setUseKeyBehavior(new Openable(chest, "Chest"));
      if (chest.get("existed").equals("true")) {
        super.setCheckBehavior(new Locked_Checkable(chest, this.LOCATION, super.useKeyBehavior));
      }
    }
  }

  public String getLocation() {
    return this.LOCATION;
  }

  @Override
  public String getName() {
    return this.NAME;
  }

  @Override
  public String getType() {
    return "chest";
  }

  @Override
  public int compareTo(String location) {
    return this.getLocation().compareTo(location);
  }

  @Override
  public String toString() {
    return (super.useKeyBehavior.get_isLocked() != null && super.useKeyBehavior.get_isLocked())
            ? "LOCKED Chest: " + this.NAME + ", in: " + this.LOCATION
            : "UNLOCKED Chest: " + this.NAME + ", in: " + this.LOCATION;
  }
}
