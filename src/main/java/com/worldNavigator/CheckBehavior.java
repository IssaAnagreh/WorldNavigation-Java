package com.worldNavigator;

import java.util.HashMap;

public interface CheckBehavior {
  HashMap check_content(String location, PlayerModel playerModel);

  HashMap acquire_contents(String location, PlayerModel playerModel);
}
