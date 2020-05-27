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

  public int compareTo(String masterKey) {
    return this.NAME.compareTo(masterKey);
  }

  public boolean equals(MasterKey masterKey) {
    if (masterKey != null) {
      return masterKey.NAME.equals(this.NAME);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
