package com.worldNavigator;

import java.util.HashMap;

public class Uncheckable implements CheckBehavior {

    public HashMap check_content(String location, PlayerModel playerModel) {
        HashMap content = new HashMap<String, Object>();
        return content;
    }

    public HashMap<String, Object> acquire_contents(String location, PlayerModel playerModel) {
        HashMap<String, Object> acquired_items = this.check_content(location, playerModel);
        return new HashMap();
    }

    @Override
    public String toString() {
        return "Uncheckable";
    }
}
