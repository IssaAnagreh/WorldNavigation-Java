package com.worldNavigator;

import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class PlayerController {
    public MapFactory mapFactory;
    public List<Room> rooms;
    public int flashLights;
    public int roomIndex = 2;
    public int golds = 10;
    public String orientation = "n";
    public String location = "c1";
    static BufferedReader br;
    public List<Key> keys = new ArrayList<Key>();

    public PlayerController(MapFactory map) {
        this.mapFactory = map;
        this.rooms = map.rooms;
    }

    public PlayerController() {
    }

    public void subscribe(PlayerViewer playerViewer) {
        this.mapFactory.addObserver(playerViewer);
    }

    public void unsubscribe(PlayerViewer playerViewer) {
        this.mapFactory.deleteObserver(playerViewer);
    }

    public void startGame() throws IOException {
        // variables declarations
        mapFactory.playing = true;
        long end_time = mapFactory.end_time;

        // start game timer
        long start = System.currentTimeMillis();
        long end = start + (1000 * end_time);
        new GameTimer((int) end_time);

        // read player console
        br = new BufferedReader(new InputStreamReader(System.in));

        while (mapFactory.playing) {
            System.out.print("Enter your next command: ");
            String command = br.readLine();
            if (mapFactory.playing) use_method(command);
        }

        // game is finished
        this.mapFactory.notify_observers("Game over");
    }

    public void endGame() throws IOException {
        br.close();
        mapFactory.playing = false;
        System.exit(1);
    }

    public void myItems() {
        System.out.println("keys: " + keys);
        System.out.println("golds: " + golds);
        System.out.println("flashLights: " + flashLights);
    }

    public void left() {
        switch (this.orientation) {
            case "n":
                this.orientation = "w";
                break;
            case "e":
                this.orientation = "n";
                break;
            case "s":
                this.orientation = "e";
                break;
            case "w":
                this.orientation = "s";
                break;
            default:
                System.out.println("Semething went wrong while rotating");
                break;
        }
        System.out.println(this.orientation);
    }

    public void right() {
        switch (this.orientation) {
            case "n":
                this.orientation = "e";
                break;
            case "e":
                this.orientation = "s";
                break;
            case "s":
                this.orientation = "w";
                break;
            case "w":
                this.orientation = "n";
                break;
            default:
                System.out.println("Semething went wrong while rotating");
                break;
        }
        System.out.println(this.orientation);
    }

    public void myLocation() {
        System.out.println(this.location);
    }

    public void myOrientation() {
        System.out.println(this.orientation);
    }

    public enum MoveParam {
        forward, backward;
    }

    public void move(MoveParam move) {
        Move new_location = new Move(this.location, this.orientation, move);
        this.location = new_location.toString();
        System.out.println(this.location);
    }

    public void nextRoom_move() {
        Move new_location = new Move(this.location, this.orientation, MoveParam.forward, true);
        System.out.println("You are in: " + new_location.toString() + " in room number: " + roomIndex + 1);
        this.location = new_location.toString();
    }

    public void wall() {
        Room room = this.rooms.get(this.roomIndex);
        if (room.lit) {
            Wall wall = room.walls.get(this.orientation);
            System.out.println(wall.toString());
        } else {
            System.out.println("Dark");
        }
    }

    public void look() {
        Room room = this.rooms.get(this.roomIndex);
        if (room.lit) {
            Wall wall = room.walls.get(this.orientation);
            System.out.println(wall.check_items_location());
        } else {
            System.out.println("Dark");
        }
    }

    public void check_room() {
        Room room = this.rooms.get(this.roomIndex);
        System.out.println(room.toString());
    }

    public void check() {
        Room room = this.rooms.get(this.roomIndex);
        Wall wall = room.walls.get(this.orientation);
        System.out.println(wall.check_item_by_location(this.location));
    }

    public void acquire_items() {
        Room room = this.rooms.get(this.roomIndex);
        Wall wall = room.walls.get(this.orientation);
        HashMap<String, Object> items = wall.acquire_items(this.location);
        if (items.get("keys") != null) ((List) items.get("keys")).forEach(emp -> keys.add((Key) emp));
        if (items.get("golds") != null) this.golds += (long) items.get("golds");
        if (items.get("flashLight") != null) this.flashLights += (long) items.get("flashLight");
    }

    public void use_key() {
        String print = "";
        if (this.keys.size() > 0) {
            Room room = this.rooms.get(this.roomIndex);
            Wall wall = room.walls.get(this.orientation);
            Openable openable = (Openable) wall.getItem(this.location);
            if (openable != null) {
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
                print = "Keys are used for locked chests and doors";
            }

        } else {
            print = "You have no keys";
        }
        System.out.println(print);
    }


    public void open() {
        Room room = this.rooms.get(this.roomIndex);
        Wall wall = room.walls.get(this.orientation);
        Door door = (Door) wall.items.get("door");
        if (door == null) {
            System.out.println("No doors to be opened");
        } else {
            Object location_item = wall.items.get("door");
            if (location_item != null) if (((ItemsContainer) location_item).getLocation().equals(location)) {
                boolean opened = false;
                for (Room room_candidate : this.rooms) {
                    String nextRoom = door.getNextRoom();
                    if (room_candidate.roomName.equals(nextRoom)) {
                        this.roomIndex = this.rooms.indexOf(room_candidate.roomName.equals(door.getNextRoom()) ? room_candidate : -1);
                        nextRoom_move();
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
        String loc = sc.nextLine();
        this.location = loc;
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

    public void seller_list(Seller seller) {
        System.out.println(seller.contents);
    }

    public void trade() {
        Room room = this.rooms.get(this.roomIndex);
        Wall wall = room.walls.get(this.orientation);
        Seller seller = (Seller) wall.items.get("seller");

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

    private void use_method(String command) throws IOException {
        switch (command) {
            case "room":
                check_room();
                break;
            case "orientation":
                myOrientation();
                break;
            case "location":
                myLocation();
                break;
            case "wall":
                wall();
                break;
            case "look":
                look();
                break;
            case "left":
                left();
                break;
            case "right":
                right();
                break;
            case "forward":
                move(MoveParam.forward);
                break;
            case "backward":
                move(MoveParam.backward);
                break;
            case "check":
                check();
                acquire_items();
                break;
            case "myItems":
                myItems();
                break;
            case "useKey":
                use_key();
                break;
            case "setloc":
                setLocation();
                break;
            case "open":
                open();
                break;
            case "trade":
                trade();
                break;
            case "quit":
                endGame();
                break;
            default:
                System.out.println("You should use a valid command");
                break;
        }
    }
}
