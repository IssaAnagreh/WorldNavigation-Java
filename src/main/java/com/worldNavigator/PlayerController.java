package com.worldNavigator;

public class PlayerController extends PlayerControllerMaster {
  public PlayerController(PlayerModel playerModel) {
    super.playerModel = playerModel;

    super.commandsMap.put("o", PlayerControllerInterface::myOrientation);
    super.commandsMap.put("loc", PlayerControllerInterface::myLocation);
    super.commandsMap.put("l", PlayerControllerInterface::rotateLeft);
    super.commandsMap.put("r", PlayerControllerInterface::rotateRight);
    super.commandsMap.put("f", playerController -> move(MoveTypes.forward));
    super.commandsMap.put("b", playerController -> move(MoveTypes.backward));
    super.commandsMap.put(
        "c",
        playerController -> {
          this.check();
          this.acquire_items();
        });
    super.commandsMap.put("items", PlayerControllerInterface::myItems);
    super.commandsMap.put("key", PlayerControllerInterface::use_key);
    super.commandsMap.put("light", PlayerControllerInterface::switchLights);
    super.commandsMap.put("flash", PlayerControllerInterface::flashLight);
    super.commandsMap.put("master", playerController -> super.playerModel.use_masterKey());
    super.commandsMap.put("setloc", PlayerControllerInterface::setLocation);
  }

  public PlayerController() {}

  @Override
  public String toString() {
    return "Player Controller";
  }
}
