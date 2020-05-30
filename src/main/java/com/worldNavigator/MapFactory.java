package com.worldNavigator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MapFactory {
  private String name;
  public List<Room> rooms = new ArrayList<>();
  public long endTime;
  private int room_counter = 0;
  public final String mapName;
  public Map<String, Object> contents;
  public String location;
  public String orientation;
  public int roomIndex;
  public JSONArray jsonRooms;

  @SuppressWarnings("unchecked")
  public MapFactory(String mapName) {
    this.mapName = mapName;

    // JSON parser object to parse read file
    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader(mapName)) {
      // Read JSON file
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
    if (map == null) {
      throw new IllegalArgumentException();
    } else {
      // Get map object within list
      name = (String) map.get("name");
      endTime = (long) map.get("end_time");
      ContentManager contentManager = new ContentManager();
      String player_string = "player";
      HashMap<String, Object> player_details = (HashMap) map.get(player_string);
      contentManager.managePlayer(player_details);
      this.contents = contentManager.getContents();
      this.location =
          (player_details).get("location") != null
              ? (player_details).get("location").toString()
              : "c3";
      this.orientation =
          (player_details).get("orientation") != null
              ? (player_details).get("orientation").toString()
              : "n";
      this.roomIndex =
          (player_details).get("roomIndex") != null
              ? Integer.parseInt((player_details).get("roomIndex").toString())
              : 0;

      this.jsonRooms = (JSONArray) map.get("rooms");
      this.jsonRooms.forEach(room -> parseRoomObject((JSONObject) room));
    }
  }

  private void parseRoomObject(JSONObject room) {
    // Get room object within list
    JSONObject roomObject = null;
    if (room != null) {
      roomObject = (JSONObject) room.get("room");
    } else {
      throw new IllegalArgumentException();
    }
    if (roomObject != null) {
      this.rooms.add(new Room(roomObject, room_counter));
      this.room_counter++;
    } else {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public String toString() {
    return name;
  }
}
