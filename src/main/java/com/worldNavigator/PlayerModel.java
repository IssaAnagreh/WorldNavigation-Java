package com.worldNavigator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class PlayerModel extends Observable {
  private final MapFactory map;
  private List<Room> rooms;
  public Room room;
  private Wall wall;
  private int roomIndex;
  public String orientation;
  public String location;
  static BufferedReader br;
  public GameTimer timer;
  public Menu menu;
  public boolean playing;
  public Map<String, Item> items = new HashMap();
  public Map<String, Object> contents;

  public PlayerModel(MapFactory map, Menu menu) {
    this.map = map;
    this.rooms = map.rooms;
    this.menu = menu;
    this.contents = map.contents;
    this.location = map.location;
    this.orientation = map.orientation;
    this.roomIndex = map.roomIndex;
    this.room = this.rooms.get(this.roomIndex);
    this.wall = this.room.walls.get(this.orientation);
  }

  public void notify_player(String msg) {
    this.setChanged();
    notifyObservers(msg);
  }

  public void startGame() {
    this.playing = true;
    long end_time = this.map.endTime;

    // start game timer
    long start = System.currentTimeMillis();
    long end = start + (1000 * end_time);
    GameTimer gameTimer = new GameTimer((int) end_time, this);
    this.timer = gameTimer;

    this.br = new BufferedReader(new InputStreamReader(System.in));
  }

  public void wall() {
    if (this.room.isLit) {
      this.wall = this.room.walls.get(this.orientation);
      this.items = this.wall.items;
      notify_player(wall.toString());
    } else {
      notify_player("Dark");
    }
  }

  public void myItems() {
    notify_player(this.contents.toString());
  }

  public void move(PlayerController.MoveParam move) {
    Transition new_location = new Transition(this);
    new_location.move(this.location, this.orientation, move);
    this.location = new_location.toString();
    notify_player(this.location);
  }

  public void nextRoom_move() {
    Transition new_location = new Transition(this);
    new_location.openNextRoom(this.location, this.orientation, PlayerController.MoveParam.forward);
    int index = this.roomIndex + 1;
    notify_player("You are in: " + new_location.toString() + " in room number: " + index);
    this.location = new_location.toString();
    this.room = this.rooms.get(this.roomIndex);
    this.wall = this.room.walls.get(this.orientation);
  }

  public void rotateLeft() {
    this.orientation = new Rotate(this.orientation, this).left();
    this.wall = room.walls.get(this.orientation);
  }

  public void rotateRight() {
    this.orientation = new Rotate(this.orientation, this).right();
    this.wall = room.walls.get(this.orientation);
  }

  public void look() {
    if (room.isLit) {
      Wall opposite_wall = this.room.walls.get(this.orientation);
      notify_player(opposite_wall.checkItems());
    } else {
      notify_player("Dark");
    }
  }

  public String getType() {
    return this.wall.itemsFactory.getType(this.location);
  }

  public void check() {
    notify_player(this.wall.itemsFactory.check_item_by_location(this.location));
  }

  public void acquire_items() {
    Item item = this.wall.itemsFactory.getItem(this.location);
    if (item != null) {
      item.applyAcquire(this.location, this);
    }
  }

  public void use_key() {
    String print = "";
    if (((ArrayList<KeyChecker>) this.contents.get("keys")).isEmpty()) {
      print = "You have no keys";
    } else {
      Item item = this.wall.itemsFactory.getItem(this.location);
      print = item != null ? item.applyUseKey((ArrayList<KeyChecker>) this.contents.get("keys")) : "Opening nothing";
    }
    notify_player(print);
  }

  public void use_masterKey() {
    String print = "";
    List<KeyChecker> masterKeysList = new ArrayList<>();
    String masterKeysString = "masterKeys";
    if (((int) this.contents.get(masterKeysString)) > 0) {
      masterKeysList.add(new MasterKey());
      Item item = this.wall.itemsFactory.getItem(this.location);
      print = item != null ? item.applyUseKey(masterKeysList) : "Opening nothing";
      this.contents.put(masterKeysString, (int) this.contents.get(masterKeysString) - 1);
    } else {
      print = "You have no master keys";
    }
    notify_player(print);
  }

  public void open() {
    Item item = this.wall.itemsFactory.getItem(this.location);
    if (item instanceof NextGoing) {
      NextGoing openable = (NextGoing) item;
      if (item.getLocation().equals(this.location)) {
        boolean isOpened = false;
        String nextRoom = openable.getNextRoom();
        if (nextRoom.contentEquals("golden")) {
          notify_player("CONGRATULATIONS! YOU WON THE GAME");
          System.exit(1);
        }
        for (Room room_candidate : this.rooms) {
          if (room_candidate.ROOM_NAME.equals(nextRoom)) {
            this.roomIndex = this.rooms.indexOf(room_candidate);
            this.nextRoom_move();
            isOpened = true;
          }
        }
        if (nextRoom.equals("")) {
          notify_player("This " + openable.getName() + " opens to nothing");
          return;
        }
        if (!isOpened && nextRoom.equals("locked")) {
          notify_player("The " + openable.getName() + " door is locked");
        }
      } else {
        notify_player("Nothing to be opened");
      }
    } else {
      notify_player("Nothing to be opened");
    }
  }

  public void setLocation() {
    Scanner sc = new Scanner(System.in);
    this.location = sc.nextLine();
  }

  public void trade() {
    Seller seller = (Seller) this.wall.items.get("seller");

    if (seller != null) {
      notify_player("This seller has: " + seller.check_content(this.location));
      notify_player("You can use buy, sell, list or finish commands");
      Scanner sc = new Scanner(System.in);
      String command = sc.nextLine();
      switch (command) {
        case "buy":
          seller_buy(seller);
          break;
        case "sell":
          seller_sell(seller);
          break;
        case "list":
          seller_list(seller);
          break;
        case "finish":
          notify_player("You quited trading");
          break;
        default:
          trade();
          break;
      }
    } else {
      notify_player("No sellers in this orientation");
    }
  }

  public void seller_list(Seller seller) {
    notify_player(seller.contents.getContents().toString());
  }

  public void seller_buy(Seller seller) {
    Map bought = seller.buy((int) this.contents.get("golds"), this);
    if (bought.size() > 0) {
      String kind = bought.get("kind").toString();
      if (kind.equals("out of bounds")) {
        notify_player("Choose an existed item's index");
        seller_buy(seller);
      } else {
        notify_player("bought " + bought);
        this.contents.put(
            "golds", ((int) this.contents.get("golds")) + ((int) bought.get("golds")));
        switch (kind) {
          case "keys":
            this.contents.put("keys", ((List) this.contents.get("keys")).add(bought.get("item")));
            break;
          case "flashLights":
            this.contents.put("flashLights", ((int) this.contents.get("flashLights")) + 1);
            break;
          default:
            notify_player("Kind is not standard");
            break;
        }
        notify_player("Item successfully bought");
        notify_player("Your Items: ");
        myItems();
        seller_buy(seller);
      }
    } else {
    }
  }

  public void seller_sell(Seller seller) {
    notify_player("Items and values this seller is willing to buy: ");
    notify_player(seller.selling.toString());
    notify_player(
        "Enter the type of the item you want to sell: "
            + seller.selling.keySet()
            + " or type quit to cancel");
    Scanner sc1 = new Scanner(System.in);
    String type = sc1.next();
    if (type.equals("quit")) {
      trade();
    } else {
      if (type.equals("keys")) {
        if (((List<KeyChecker>) this.contents.get("keys")).isEmpty()) {
          notify_player("You dont have keys to sell");
          seller_sell(seller);
        } else {
          notify_player(
              "Enter the name of the item you want to sell: " + this.contents.get("keys"));
          Scanner sc2 = new Scanner(System.in);
          String item = sc2.next();
          for (KeyChecker key : ((List<KeyChecker>) this.contents.get("keys"))) {
            if (key.toString().equals(item)) {
              ((List<KeyChecker>) this.contents.get("keys")).remove(key);
              break;
            }
          }
          this.contents.put("golds", seller.sell((int) this.contents.get("golds"), type, this));
        }
      } else if (type.equals("flashLights")) {
        if (((int) this.contents.get("flashLights")) > 0) {
          this.contents.put("flashLights", ((int) this.contents.get("flashLights")) - 1);
          this.contents.put("golds", seller.sell((int) this.contents.get("golds"), type, this));
          notify_player("Your Items: ");
          myItems();
        } else {
          notify_player("You dont have flashLights to sell");
          seller_sell(seller);
        }
      } else {
        notify_player("Choose a correct type");
        seller_sell(seller);
      }
    }
  }

  public void switchLights() {
    this.room.switchLights(this);
  }

  public void flashLight() {
    if (this.room.isLit) {
      notify_player("You don't need to light a lit room");
      return;
    }
    if ((int) this.contents.get("flashLights") > 0) {
      this.contents.put(
          "flashLights", this.room.useFlashLight((int) this.contents.get("flashLights"), this));
    } else {
      notify_player("You have no flashLights");
    }
  }

  @Override
  public String toString() {
    return "Player model";
  }
}
