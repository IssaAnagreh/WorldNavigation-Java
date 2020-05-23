package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public abstract class Checkable implements CheckBehavior {
  private ContentManager contents;
  protected final String LOCATION;

  public Checkable(String location) {
    this.LOCATION = location;
  }

  protected void setContents(JSONObject item) {
    this.contents = new ContentManager();
    this.contents.manageItem(item);
  }

  protected ContentManager getContents() {
    return contents;
  }

  public void acquireContents(String location, PlayerModel playerModel) {
    Map<String, Object> acquiredContents = this.checkContent(location, playerModel);
    if (!acquiredContents.isEmpty()) {
      if (acquiredContents.get("keys") != null) {
        ((List<?>) acquiredContents.get("keys"))
            .forEach(((List) playerModel.contents.get("keys"))::add);
      }

      String goldsString = "golds";
      if (acquiredContents.get(goldsString) != null) {
        playerModel.contents.put(
            goldsString,
            ((int) playerModel.contents.get(goldsString)) + ((int) acquiredContents.get(goldsString)));
      }

      String flashLightString = "flashLights";
      if (acquiredContents.get(flashLightString) != null) {
        playerModel.contents.put(
            flashLightString,
            ((int) playerModel.contents.get(flashLightString))
                + ((int) acquiredContents.get(flashLightString)));
      }

      String masterKeyString = "masterKeys";
      if (acquiredContents.get(masterKeyString) != null) {
        playerModel.contents.put(
            masterKeyString,
            ((int) playerModel.contents.get(masterKeyString))
                + ((int) acquiredContents.get(masterKeyString)));
      }

      playerModel.notify_player("Contents acquired " + acquiredContents);
    }
  }
}
