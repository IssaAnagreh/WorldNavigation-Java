package com.worldNavigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PlayerModel extends Observable {
    public MapFactory map;
    public List<Room> rooms;
    public Room room;
    public Wall wall;
    public int flashLights = 1;
    public int roomIndex = 0;
    public int golds = 10;
    public String orientation = "n";
    public String location = "c3";
    static BufferedReader br;
    public List<Key> keys = new ArrayList<Key>();
    public GameTimer timer;
    public Menu menu;
    public boolean playing;

    public PlayerModel(MapFactory map, Menu menu) {
        this.map = map;
        this.rooms = map.rooms;
        this.menu = menu;
        this.room = this.rooms.get(0);
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
        GameTimer gameTimer = new GameTimer((int) end_time);
        this.timer = gameTimer;

        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void wall() {
        if (this.room.lit) {
            Wall wall = this.room.walls.get(this.orientation);
            System.out.println(wall.toString());
        } else {
            System.out.println("Dark");
        }
    }

    public void myItems() {
        System.out.println("keys: " + this.keys);
        System.out.println("golds: " + this.golds);
        System.out.println("flashLights: " + this.flashLights);
    }

    public void move(PlayerController.MoveParam move) {
        Move new_location = new Move(this.location, this.orientation, move);
        this.location = new_location.toString();
        System.out.println(this.location);
    }

    public void nextRoom_move() {
        Move new_location = new Move(this.location, this.orientation, PlayerController.MoveParam.forward, true);
        int index = this.roomIndex + 1;
        System.out.println("You are in: " + new_location.toString() + " in room number: " + index);
        this.location = new_location.toString();
        this.room = this.rooms.get(this.roomIndex);
    }

    public void rotateLeft() {
        this.orientation = new Rotate(this.orientation).left();
        this.wall = room.walls.get(this.orientation);
    }

    public void rotateRight() {
        this.orientation = new Rotate(this.orientation).right();
        this.wall = room.walls.get(this.orientation);
    }

    public void myLocation() {
        System.out.println(this.location);
    }

    public void myOrientation() {
        System.out.println(this.orientation);
    }

    public void look() {
        if (room.lit) {
            Wall wall = this.room.walls.get(this.orientation);
            System.out.println(wall.check_items_location());
        } else {
            System.out.println("Dark");
        }
    }

    public void check() {
        System.out.println(wall.check_item_by_location(this.location));
    }

    public void acquire_items() {
        HashMap<String, Object> items = this.wall.acquire_items(this.location);
        if (items.get("keys") != null) ((List) items.get("keys")).forEach(emp -> this.keys.add((Key) emp));
        if (items.get("golds") != null) this.golds += (long) items.get("golds");
        if (items.get("flashLight") != null) this.flashLights += (long) items.get("flashLight");
    }

    public void use_key() {
        String print = "";
        if (this.keys.size() > 0) {
            Openable openable = (Openable) this.wall.getItem(this.location);
            if (openable != null) {
                if (openable.getIs_locked()) {
                    boolean locked = true;
                    for (Key keyItem : this.keys) {
                        locked = !locked ? false : keyItem.unlock(openable);
                        openable.setIs_locked(locked);
                    }
                    if (!locked) {
                        print = "Object is open now";
                    } else {
                        print = "Look for a suitable key";
                    }
                } else {
                    print = "Object is already open";
                }
            } else {
                print = "Keys are used for locked chests and doors";
            }

        } else {
            print = "You have no keys";
        }
        System.out.println(print);
    }

    public void open() {
        Door door = (Door) this.wall.items.get("door");
        if (door == null) {
            System.out.println("No doors to be opened");
        } else {
            if (((ItemsContainer) door).getLocation().equals(this.location)) {
                boolean opened = false;
                for (Room room_candidate : this.rooms) {
                    String nextRoom = door.getNextRoom();
                    if (room_candidate.roomName.equals(nextRoom)) {
                        this.roomIndex = this.rooms.indexOf(room_candidate.roomName.equals(door.getNextRoom()) ? room_candidate : -1);
                        this.nextRoom_move();
                        opened = true;
                        System.out.println("Opened");
                    }
                }
                if (!opened) System.out.println("The door is locked or no doors to be opened");
            } else {
                System.out.println("No doors to be opened");
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
            System.out.println("This seller has: " + seller.check_content(this.location));
            System.out.println("You can use buy, sell, list or finish commands");
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
                    System.out.println("You quited trading");
                    break;
                default:
                    trade();
                    break;
            }
        } else {
            System.out.println("No sellers in this orientation");
        }
    }

    public void seller_list(Seller seller) {
        System.out.println(seller.contents);
    }

    public void seller_buy(Seller seller) {
        HashMap bought = seller.buy(this.golds);
        if (bought.size() > 0) {
            String kind = bought.get("kind").toString();
            if (kind.equals("out of bounds")) {
                System.out.println("Choose an existed item's index");
                seller_buy(seller);
            } else {
                this.golds = (int) bought.get("golds");
                switch (kind) {
                    case "Keys":
                        this.keys.add(((Key) bought.get("item")));
                        break;
                    case "FlashLights":
                        this.flashLights += 1;
                        break;
                    default:
                        System.out.println("Kind is not standard");
                        break;
                }
                System.out.println("Item successfully bought");
                System.out.println("Your Items: ");
                myItems();
                seller_buy(seller);
            }
        } else {
        }
    }

    public void seller_sell(Seller seller) {
        System.out.println("Items and values this seller is willing to buy: ");
        System.out.println(seller.selling);
        System.out.println("Enter the type of the item you want to sell: " + seller.selling.keySet() + " or type quit to cancel");
        Scanner sc1 = new Scanner(System.in);
        String type = sc1.next();
        if (type.equals("quit")) {
            trade();
        } else {
            if (type.equals("keys")) {
                if (this.keys.size() > 0) {
                    System.out.println("Enter the name of the item you want to sell: " + this.keys);
                    Scanner sc2 = new Scanner(System.in);
                    String item = sc2.next();
                    for (Key key : this.keys) {
                        System.out.println(key.toString().equals(item));
                        if (key.toString().equals(item)) {
                            this.keys.remove(key);
                            break;
                        }
                    }
                    this.golds = seller.sell(this.golds, type);
                } else {
                    System.out.println("You dont have keys to sell");
                    seller_sell(seller);
                }
            } else if (type.equals("flashLights")) {
                if (this.flashLights > 0) {
                    this.flashLights--;
                    this.golds = seller.sell(this.golds, type);
                    System.out.println("Your Items: ");
                    myItems();
                } else {
                    System.out.println("You dont have keys to sell");
                    seller_sell(seller);
                }
            } else {
                System.out.println("Choose a correct type");
                seller_sell(seller);
            }
        }
    }

    public void switchLights() {
        this.room.switchLights();
    }

    public void flashLight() {
        if (this.room.lit) {
            System.out.println("You dont need to light a lit room");
            return;
        }
        if (this.flashLights > 0) {
            this.flashLights = this.room.useFlashLight(this.flashLights);
        } else {
            System.out.println("You have no flashLights");
        }
    }

    public ArrayList get_command() {
        return this.menu.get_commands();
    }

    public String use_command(String command) {
        if (this.menu.get_commands().contains(command)) {
            return command;
        }
        return "";
    }
}
