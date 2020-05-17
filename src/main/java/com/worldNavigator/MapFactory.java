package com.worldNavigator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapFactory {
    private String name;
    public List<Room> rooms = new ArrayList();
    public long end_time;
    private int room_counter = 0;
    public final String mapName;
    public int golds;
    public int flashLights;
    public ArrayList keys;
    public String location;
    public String orientation;
    public int roomIndex;

    @SuppressWarnings("unchecked")
    public MapFactory(String mapName) {
        this.mapName = mapName;

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(mapName)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray maps = (JSONArray) obj;
            maps.forEach(map -> parseMapObject((JSONObject) map));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseMapObject(JSONObject map) {
        if (map != null) {
            //Get map object within list
            name = (String) map.get("name");
            end_time = (long) map.get("end_time");
            String player_string = "player";
            this.golds = Integer.parseInt(((HashMap) map.get(player_string)).get("golds").toString());
            this.flashLights = Integer.parseInt(((HashMap) map.get(player_string)).get("flashLights").toString());
            this.keys = new ArrayList();
            this.location = ((HashMap) map.get(player_string)).get("location").toString();
            this.orientation = ((HashMap) map.get(player_string)).get("orientation").toString();
            this.roomIndex = Integer.parseInt(((HashMap) map.get(player_string)).get("roomIndex").toString());

            JSONArray jsonRooms = (JSONArray) map.get("rooms");
            jsonRooms.forEach(room -> parseRoomObject((JSONObject) room));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void parseRoomObject(JSONObject room) {
        //Get room object within list
        JSONObject roomObject = null;
        if (room != null) roomObject = (JSONObject) room.get("room");
        if (roomObject != null) {
            rooms.add(new Room(roomObject, room_counter));
            this.room_counter++;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}