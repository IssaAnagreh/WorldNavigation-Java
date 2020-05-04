package com.worldNavigator;

import java.util.ArrayList;
import java.util.List;

public class Maps {
    public List<MapFactory> maps = new ArrayList();

    public List<MapFactory> getMaps() {
        return maps;
    }

    public void addMap(String json) {
        MapFactory khareeta = new MapFactory(json);
        maps.add(khareeta);
    };

    public void removeMap(MapFactory map) {
        maps.remove(map);
    }
}
