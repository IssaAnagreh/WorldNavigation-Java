package com.worldNavigator;

import java.util.HashMap;

public class Uncheckable implements CheckBehavior {

  public HashMap check_content(String location, PlayerModel playerModel) {
    HashMap content = new HashMap<String, Object>();
    return content;
  }

  @Override
  public void acquire_contents(String location, PlayerModel playerModel) {
    return;
  }

  @Override
  public String toString() {
    return "Uncheckable";
  }
}
