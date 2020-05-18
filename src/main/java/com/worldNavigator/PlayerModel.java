package com.worldNavigator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class PlayerModel extends Observable {
    private final MapFactory map;
    private List<Room> rooms;
    public Room room;
    private Wall wall;
    private int flashLights;
    private int roomIndex;
    public int golds;
    public String orientation;
    public String location;
    static BufferedReader br;
    public List<Key> keys;
    public GameTimer timer;
    public Menu menu;
    public boolean playing;
    public HashMap<String, Item> items = new HashMap();

    public PlayerModel(MapFactory map, Menu menu) {
        this.map = map;
        this.rooms = map.rooms;
        this.menu = menu;
        this.flashLights = map.flashLights;
        this.golds = map.golds;
        this.keys = map.keys;
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
        long end_time = this.map.end_time;

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
        notify_player("keys: " + this.keys);
        notify_player("golds: " + this.golds);
        notify_player("flashLights: " + this.flashLights);
    }

    public void move(PlayerControllerMaster.MoveParam move) {
        Transition new_location = new Transition(this);
        new_location.move(this.location, this.orientation, move);
        this.location = new_location.toString();
        notify_player(this.location);
    }

    public void nextRoom_move() {
        Transition new_location = new Transition(this);
        new_location.openNextRoom(this.location, this.orientation, PlayerControllerMaster.MoveParam.forward);
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

    public void myLocation() {
        notify_player(this.location);
    }

    public void myOrientation() {
        notify_player(this.orientation);
    }

    public void look() {
        if (room.isLit) {
            Wall wall = this.room.walls.get(this.orientation);
            notify_player(wall.check_items());
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
            HashMap contents = item.applyAcquire(this.location, this);
            if (contents.get("keys") != null) {
                ((List) contents.get("keys")).forEach(emp -> this.keys.add((Key) emp));
            }
            if (contents.get("golds") != null) {
                this.golds += (long) contents.get("golds");
            }
            if (contents.get("flashLight") != null) {
                this.flashLights += (long) contents.get("flashLight");
            }
            if (contents.size() > 0) {
                notify_player("Contents acquired " + contents);
            }
        }
    }

    public void use_key() {
        String print = "";
        if (this.keys.size() > 0) {
            Item item = this.wall.itemsFactory.getItem(this.location);
            print = item.applyUseKey(this.keys);
        } else {
            print = "You have no keys";
        }
        notify_player(print);
    }

    public void open() {
        Door door = (Door) this.wall.items.get("door");
        if (door == null) {
            notify_player("No doors to be opened");
        } else {
            if (door.getLocation().equals(this.location)) {
                boolean isOpened = false;
                String nextRoom = door.getNextRoom();
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
                    notify_player("This door opens to nothing");
                    return;
                }
                if (!isOpened && nextRoom.equals("locked")) {
                    notify_player("The door is locked");
                }
            } else {
                notify_player("No doors to be opened");
            }
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
        HashMap bought = seller.buy(this.golds, this);
        if (bought.size() > 0) {
            String kind = bought.get("kind").toString();
            if (kind.equals("out of bounds")) {
                notify_player("Choose an existed item's index");
                seller_buy(seller);
            } else {
                notify_player("bought " + bought);
                this.golds = (int) bought.get("golds");
                switch (kind) {
                    case "keys":
                        this.keys.add(((Key) bought.get("item")));
                        break;
                    case "flashLights":
                        this.flashLights += 1;
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
        notify_player("Enter the type of the item you want to sell: " + seller.selling.keySet() + " or type quit to cancel");
        Scanner sc1 = new Scanner(System.in);
        String type = sc1.next();
        if (type.equals("quit")) {
            trade();
        } else {
            if (type.equals("keys")) {
                if (this.keys.size() > 0) {
                    notify_player("Enter the name of the item you want to sell: " + this.keys);
                    Scanner sc2 = new Scanner(System.in);
                    String item = sc2.next();
                    for (Key key : this.keys) {
                        if (key.toString().equals(item)) {
                            this.keys.remove(key);
                            break;
                        }
                    }
                    this.golds = seller.sell(this.golds, type, this);
                } else {
                    notify_player("You dont have keys to sell");
                    seller_sell(seller);
                }
            } else if (type.equals("flashLights")) {
                if (this.flashLights > 0) {
                    this.flashLights--;
                    this.golds = seller.sell(this.golds, type, this);
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
        if (this.flashLights > 0) {
            this.flashLights = this.room.useFlashLight(this.flashLights, this);
        } else {
            notify_player("You have no flashLights");
        }
    }

    @Override
    public String toString() {
        return "Player model";
    }
}
