package com.worldNavigator;

import java.io.IOException;
import java.util.*;

public class PlayerControllerMaster implements PlayerControllerInterface {
  PlayerModel playerModel;
  private ArrayList<String> door_commands = new ArrayList<>();
  private ArrayList<String> chest_commands = new ArrayList<>();
  private ArrayList<String> mirror_commands = new ArrayList<>();
  private ArrayList<String> painting_commands = new ArrayList<>();
  private ArrayList<String> seller_commands = new ArrayList<>();
  private boolean hint = true;
  private Map<String, Command> commandsMap;
  private final String HINT = ", use <hints> command to stop these hints";

  public PlayerControllerMaster() {
    String checkString = "check";
    chest_commands.add(checkString);
    mirror_commands.add(checkString);
    painting_commands.add(checkString);
    String useKeyString = "useKey";
    chest_commands.add(useKeyString);
    door_commands.add(useKeyString);
    door_commands.add("open");
    String tradeString = "trade";
    seller_commands.add(tradeString);
  }

  public void subscribe(PlayerViewer playerViewer) {
    this.playerModel.addObserver(playerViewer);
  }

  public void startGame() throws IOException {
    this.playerModel.startGame();
    this.init_commands();
    // commands entering method
    while (this.playerModel.playing) {
      this.playerModel.notify_player("Enter your next command: ");
      String command = this.playerModel.br.readLine();
      if (this.playerModel.playing) use_method(command.trim().toLowerCase());
    }
  }

  public void init_commands() {
    Map<String, Command> c = new HashMap<>();

    c.put("room", PlayerControllerInterface::room);
    c.put(
        "orientation",
        playerController -> {
          this.myOrientation();
          if (this.hint)
            this.playerModel.notify_player("You can use <o> as a shortcut command" + this.HINT);
        });
    c.put("o", PlayerControllerInterface::myOrientation);
    c.put(
        "location",
        playerController -> {
          this.myLocation();
          if (this.hint)
            this.playerModel.notify_player("You can use <loc> as a shortcut command" + this.HINT);
        });
    c.put("loc", PlayerControllerInterface::myLocation);
    c.put("wall", PlayerControllerInterface::wall);
    c.put("look", PlayerControllerInterface::look);
    c.put(
        "left",
        playerController -> {
          this.rotateLeft();
          if (this.hint)
            this.playerModel.notify_player("You can use <l> as a shortcut command" + this.HINT);
        });
    c.put("l", PlayerControllerInterface::rotateLeft);
    c.put(
        "right",
        playerController -> {
          this.rotateRight();
          if (this.hint)
            this.playerModel.notify_player("You can use <r> as a shortcut command" + this.HINT);
        });
    c.put("r", PlayerControllerInterface::rotateRight);
    c.put(
        "forward",
        playerController -> {
          move(PlayerControllerMaster.MoveParam.forward);
          if (this.hint)
            this.playerModel.notify_player("You can use <f> as a shortcut command" + this.HINT);
        });
    c.put("f", playerController -> move(PlayerControllerMaster.MoveParam.forward));
    c.put(
        "backward",
        playerController -> {
          move(PlayerControllerMaster.MoveParam.backward);
          if (this.hint)
            this.playerModel.notify_player("You can use <b> as a shortcut command" + this.HINT);
        });
    c.put("b", playerController -> move(PlayerControllerMaster.MoveParam.backward));
    c.put(
        "check",
        playerController -> {
          this.check();
          this.acquire_items();
          if (this.hint)
            this.playerModel.notify_player("You can use <c> as a shortcut command" + this.HINT);
        });
    c.put(
        "c",
        playerController -> {
          this.check();
          this.acquire_items();
        });
    c.put(
        "myItems",
        playerController -> {
          this.myItems();
          if (this.hint)
            this.playerModel.notify_player("You can use <items> as a shortcut command" + this.HINT);
        });
    c.put("items", PlayerControllerInterface::myItems);
    c.put(
        "useKey",
        playerController -> {
          this.use_key();
          if (this.hint)
            this.playerModel.notify_player("You can use <key> as a shortcut command" + this.HINT);
        });
    c.put("key", PlayerControllerInterface::use_key);
    c.put("open", PlayerControllerInterface::open);
    c.put("trade", PlayerControllerInterface::trade);
    c.put(
        "switchLight",
        playerController -> {
          this.switchLights();
          if (this.hint)
            this.playerModel.notify_player("You can use <light> as a shortcut command" + this.HINT);
        });
    c.put("light", PlayerControllerInterface::switchLights);
    c.put(
        "flashLight",
        playerController -> {
          this.flashLight();
          if (this.hint)
            this.playerModel.notify_player("You can use <flash> as a shortcut command" + this.HINT);
        });
    c.put("flash", PlayerControllerInterface::flashLight);
    c.put("setloc", PlayerControllerInterface::setLocation);
    c.put("commands", PlayerControllerInterface::commands);
    c.put("time", PlayerControllerInterface::time);
    c.put("hints", PlayerControllerInterface::switchHints);
    c.put("quit", PlayerControllerInterface::quit);

    this.commandsMap = Collections.unmodifiableMap(c);
  }

  public void myItems() {
    this.playerModel.myItems();
  }

  public void rotateLeft() {
    this.playerModel.rotateLeft();
  }

  public void rotateRight() {
    this.playerModel.rotateRight();
  }

  public void myLocation() {
    this.playerModel.notify_player(this.playerModel.location);
  }

  public void myOrientation() {
    this.playerModel.notify_player(this.playerModel.orientation);
  }

  public enum MoveParam {
    forward,
    backward;
  }

  public void move(PlayerControllerMaster.MoveParam move) {
    this.playerModel.move(move);
  }

  public void wall() {
    this.playerModel.wall();
  }

  public void look() {
    this.playerModel.look();
  }

  public void room() {
    this.playerModel.notify_player(this.playerModel.room.toString());
  }

  public void check() {
    this.playerModel.check();
  }

  public String getType() {
    return this.playerModel.getType();
  }

  public void acquire_items() {
    this.playerModel.acquire_items();
  }

  public void use_key() {
    this.playerModel.use_key();
  }

  public void open() {
    this.playerModel.open();
  }

  public void setLocation() {
    this.playerModel.setLocation();
  }

  public void trade() {
    this.playerModel.trade();
  }

  public void switchLights() {
    this.playerModel.switchLights();
  }

  public void flashLight() {
    this.playerModel.flashLight();
  }

  public void time() {
    this.playerModel.timer.getRemaining_time();
  }

  public void commands() {
    String type = this.getType();
    switch (type + "") {
      case "door":
        this.playerModel.notify_player(this.door_commands.toString());
        break;
      case "seller":
        this.playerModel.notify_player(this.seller_commands.toString());
        break;
      case "mirror":
        this.playerModel.notify_player(this.mirror_commands.toString());
        break;
      case "painting":
        this.playerModel.notify_player(this.painting_commands.toString());
        break;
      case "chest":
        this.playerModel.notify_player(this.chest_commands.toString());
        break;
      default:
        this.playerModel.notify_player(this.commandsMap.keySet().toString());
    }
  }

  public void switchHints() {
    this.hint = !this.hint;
    this.playerModel.notify_player("Hints are " + (this.hint ? "on" : "off"));
  }

  public void restart() {
    try {
      this.playerModel.menu.restart();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void quit() {
    this.playerModel.menu.quit();
  }

  public void use_method(String command) {
    Command c = this.commandsMap.get(command);
    if (c == null) {
      this.playerModel.notify_player("You should use a valid command");
    } else {
      c.applyCommand(this);
    }
  }

  @Override
  public String toString() {
    return "Player Controller Master";
  }
}
