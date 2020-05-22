package com.worldNavigator;

public class MasterKey extends Key implements KeyChecker {
  private final String NAME;

  public MasterKey() {
    super("master");
    this.NAME = "master";
  }

  @Override
  public String getName() {
    return this.NAME;
  }

  @Override
  public Boolean unlock(Openable openable) {
    return true;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
