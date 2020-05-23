package com.worldNavigator;

import java.util.Map;

public interface CheckBehavior {
  Map<String, Object> check_content(String location, PlayerModel playerModel);

  void acquire_contents(String location, PlayerModel playerModel);
}
