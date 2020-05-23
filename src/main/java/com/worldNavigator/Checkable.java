package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public abstract class Checkable implements CheckBehavior {
  protected ContentManager contents;
  protected final String LOCATION;

  public Checkable(JSONObject item, String location) {
    this.contents = new ContentManager();
    this.contents.addItem(item);
    this.LOCATION = location;
  }

  public void acquire_contents(String location, PlayerModel playerModel) {
    Map<String, Object> acquired_contents = this.check_content(location, playerModel);
    if (!acquired_contents.isEmpty()) {
      if (acquired_contents.get("keys") != null) {
        ((List<?>) acquired_contents.get("keys"))
            .forEach(((List) playerModel.contents.get("keys"))::add);
      }

      String goldsString = "golds";
      if (acquired_contents.get(goldsString) != null) {
        playerModel.contents.put(
            goldsString,
            ((int) playerModel.contents.get(goldsString)) + ((int) acquired_contents.get(goldsString)));
      }

      String flashLightString = "flashLights";
      if (acquired_contents.get(flashLightString) != null) {
        playerModel.contents.put(
            flashLightString,
            ((int) playerModel.contents.get(flashLightString))
                + ((int) acquired_contents.get(flashLightString)));
      }

      String masterKeyString = "masterKeys";
      if (acquired_contents.get(masterKeyString) != null) {
        playerModel.contents.put(
            masterKeyString,
            ((int) playerModel.contents.get(masterKeyString))
                + ((int) acquired_contents.get(masterKeyString)));
      }

      playerModel.notify_player("Contents acquired " + acquired_contents);
    }
  }
}
