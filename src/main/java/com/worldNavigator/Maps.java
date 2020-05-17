package com.worldNavigator;

import java.util.ArrayList;
import java.util.List;

public class Maps {
    public List<MapFactory> maps = new ArrayList();

    public MapFactory generate(String json) {
        MapFactory khareeta = new MapFactory(json);
        return khareeta;
    }

    public void addMap(String json) {
        maps.add(generate(json));
    };

    public void removeMap(MapFactory map) {
        maps.remove(map);
    }

    public void replace(MapFactory map, int index) {
        this.maps.set(index, map);
    }

    @Override
    public String toString() {
        return "Maps: " + this.maps.toString();
    }
}
