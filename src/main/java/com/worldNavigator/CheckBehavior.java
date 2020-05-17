package com.worldNavigator;

import java.util.HashMap;

public interface CheckBehavior {
    HashMap check_content(String location);
    HashMap acquire_contents(String location);
}
