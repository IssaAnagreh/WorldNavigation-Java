package com.worldNavigator;

import java.util.HashMap;
import java.util.Map;

public class Uncheckable implements CheckBehavior {

  public Map<String, Object> check_content(String location, PlayerModel playerModel) {
    return new HashMap();
  }

  @Override
  public void acquire_contents(String location, PlayerModel playerModel) {
    // this method is meaningless in Uncheckable items
  }

  @Override
  public String toString() {
    return "Uncheckable";
  }
}
