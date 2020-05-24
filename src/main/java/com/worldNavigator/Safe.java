package com.worldNavigator;

import org.json.simple.JSONObject;

public class Safe extends Item {
  private final String NAME;
  private final String LOCATION;

  public Safe(JSONObject safe) {
    this.NAME = (String) safe.get("name");
    this.LOCATION = (String) safe.get("location");

    if (safe.get("key") != null) {
      super.setUseKeyBehavior(new Openable(safe, "Safe"));
      if (safe.get("existed").equals("true")) {
        super.setCheckBehavior(new Locked_Checkable(safe, this.LOCATION, super.useKeyBehavior));
      }
    } else {
      if (safe.get("existed").equals("true")) {
        super.setCheckBehavior(new Unlocked_Checkable(safe, this.LOCATION));
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
    return "safe";
  }

  @Override
  public int compareTo(String location) {
    return this.getLocation().compareTo(location);
  }

  @Override
  public String toString() {
    return (super.useKeyBehavior.get_isLocked() != null && super.useKeyBehavior.get_isLocked())
            ? "LOCKED Safe: " + this.NAME + ", in: " + this.LOCATION
            : "UNLOCKED Safe: " + this.NAME + ", in: " + this.LOCATION;
  }
}
