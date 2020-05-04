package com.worldNavigator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapFactory extends Observable {
    public String name;
    public List<Room> rooms = new ArrayList();
    public long end_time;
    public long golds_increment = -1;
    public static boolean playing = false;

    @SuppressWarnings("unchecked")
    public MapFactory(String mapName) {
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
            golds_increment = (long) map.get("golds_increment");
            JSONArray rooms = (JSONArray) map.get("rooms");
            rooms.forEach(room -> parseRoomObject((JSONObject) room));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void parseRoomObject(JSONObject room) {
        //Get room object within list
        JSONObject roomObject = null;
        if (room != null) roomObject = (JSONObject) room.get("room");
        if (roomObject != null) rooms.add(new Room(roomObject));
    }

    public void notify_observers(String msg) {
        this.setChanged();
        notifyObservers(msg);
    }

    @Override
    public String toString() {
        return name;
    }
}


////Get room object within list
//JSONObject roomObject = (JSONObject) room.get("room");
//System.out.println(roomObject.get("name"));
//JSONArray doors = (JSONArray) roomObject.get("n_wall").get("door").get("name");
//JSONObject n_wall = (JSONObject) roomObject.get("n_wall");
//JSONObject n_door = (JSONObject) n_wall.get("door");
//System.out.println("north door: " + n_door.get("existed"));
//if (n_door.get("existed").equals("true")) System.out.println("north door: " + n_door.get("name"));
//doors.forEach( emp -> System.out.println(emp) );


//Door door = new Door();
//
//KeyChecker key = new Key(door.toString());
//
//System.out.println(door.accept(key));