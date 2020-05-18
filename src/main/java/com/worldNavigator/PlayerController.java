package com.worldNavigator;

public class PlayerController extends PlayerControllerMaster {
  public PlayerController(PlayerModel playerModel) {
    super.playerModel = playerModel;
  }

  public PlayerController() {}

  @Override
  public String toString() {
    return "Player Controller";
  }
}
