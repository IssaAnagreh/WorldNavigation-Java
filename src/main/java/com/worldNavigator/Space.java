package com.worldNavigator;

public class Space extends Item {
  private final String NAME;

  public Space() {
    this.NAME = "Space";
  }

  @Override
  public String getLocation() {
    return "Space";
  }

  @Override
  public String getName() {
    return this.NAME;
  }

  @Override
  public String getType() {
    return "nothing";
  }

  @Override
  public int compareTo(String location) {
    return this.getLocation().compareTo(location);
  }

  public boolean equals(Space space) {
    if (space != null) {
      return space.NAME.equals(this.NAME);
    } else {
      return false;
    }
  }

  public int hashCode() {
    return this.NAME.hashCode();
  }

  @Override
  public String toString() {
    return this.NAME;
  }
}
