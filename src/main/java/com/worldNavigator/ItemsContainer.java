package com.worldNavigator;

import java.util.HashMap;

public interface ItemsContainer {
    public String getLocation();
    public HashMap check_content(String location);
    public String getName();
    public String getDetails();
}
