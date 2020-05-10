package com.worldNavigator;

import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class PlayerController extends PlayerControllerMaster {
    public PlayerController(PlayerModel playerModel) {
        super.playerModel = playerModel;
    }

    public PlayerController() {
    }
}
