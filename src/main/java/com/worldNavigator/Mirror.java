package com.worldNavigator;

import org.json.simple.JSONObject;

public class Mirror extends Item {
  private final String LOCATION;
  private final String NAME = "Mirror";

  public Mirror(JSONObject mirror) {
    this.LOCATION = (String) mirror.get("location");
    if (mirror.get("existed").equals("true")) {
      super.setCheckBehavior(new Unlocked_Checkable(mirror, this.LOCATION));
    }
  }

  public String getLocation() {
    return this.LOCATION;
  }

  @Override
  public String getName() {
    return this.NAME + ", You See a silhouette of you";
  }

  public String getType() {
    return "mirror";
  }

  @Override
  public int compareTo(String location) {
    return this.getLocation().compareTo(location);
  }

  public boolean equals(Mirror mirror) {
    if (mirror != null) {
      return mirror.LOCATION.equals(this.LOCATION);
    } else {
      return false;
    }
  }

  public int hashCode() {
    return this.LOCATION.hashCode();
  }

  @Override
  public String toString() {
    return "Mirror: " + this.NAME + " in: " + this.LOCATION;
  }
}
