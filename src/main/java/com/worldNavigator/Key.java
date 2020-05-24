package com.worldNavigator;

public class Key implements KeyChecker {
  private final String NAME;

  public Key(String name) {
    this.NAME = name;
  }

  public String getName() {
    return this.NAME;
  }

  public Boolean unlock(Openable openable) {
    return compareTo(openable.getKey()) == 0;
  }

  public int compareTo(String key) {
    return this.NAME.compareTo(key);
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
