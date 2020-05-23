package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class Seller extends Item {
  public final String LOCATION;
  public final String NAME = "Seller";
  HashMap<String, Integer> selling = new HashMap<>();
  public ContentManager contents;

  public Seller(JSONObject seller) {
    this.LOCATION = (String) seller.get("location");
    if (seller.get("existed").equals("true")) {
      super.setCheckBehavior(new Uncheckable());
      this.contents = new ContentManager();
      this.contents.manageSellerItem(seller);
    }
    if (seller.get("selling") != null) {
      JSONObject temp_selling = (JSONObject) seller.get("selling");
      for (Object item_name : temp_selling.keySet()) {
        this.selling.put(
            item_name.toString(), Integer.parseInt(temp_selling.get(item_name).toString()));
      }
    }
  }

  public String getLocation() {
    return this.LOCATION;
  }

  public Map check_content(String location) {
    return this.contents.getContents();
  }

  public Map buy(int gold, PlayerModel playerModel) {
    Map output = new HashMap<>();

    int counter = 0;
    List categories = new ArrayList();
    playerModel.notify_player("Items ready to be sold:");
    playerModel.notify_player(-1 + ": " + "Quit");
    for (Object category : this.contents.getContents().keySet()) {
      for (Object item : ((List) this.contents.getContents().get(category))) {
        playerModel.notify_player(counter + ": " + category + " " + item);
        categories.add(category);
        categories.add(item);
        counter++;
      }
    }

    playerModel.notify_player("Type the number of the item you want to buy");
    Scanner sc = new Scanner(System.in);
    int index = sc.nextInt();
    if (index != -1) {
      if (!categories.isEmpty()) {
        String category_name;
        if (categories.size() > (index * 2) && index >= 0) {
          category_name = categories.get(index * 2).toString();
          output.put("kind", category_name);
        } else {
          output.put("kind", "out of bounds");
          return output;
        }

        Object category = this.contents.getContents().get(category_name);
        if (category != null) {
          HashMap item = (HashMap) categories.get((index * 2) + 1);
          if (item != null) {
            for (Object item_name : item.keySet()) {
              output.put("item", item.get("name"));
              if (gold - Integer.parseInt(item.get(item_name).toString()) >= 0) {
                output.put("golds", gold - Integer.parseInt(item.get(item_name).toString()));
                return output;
              } else {
                playerModel.notify_player("Return when you have enough gold");
                return new HashMap<>();
              }
            }
          } else {
            playerModel.notify_player("This index is not available");
            return new HashMap<>();
          }
        } else {
          playerModel.notify_player("This category is not available");
          return new HashMap<>();
        }
      } else {
        playerModel.notify_player("Seller has nothing to sell");
        return new HashMap<>();
      }
    } else {
      playerModel.notify_player("You quited trading");
      return output;
    }
    return output;
  }

  public int sell(int golds, String type, PlayerModel playerModel) {
    int output = golds;
    if (this.selling.get(type) != null) {
      output = golds + this.selling.get(type);
    } else {
      playerModel.notify_player("This item is not in the list");
    }

    return output;
  }

  public void sellingList(PlayerModel playerModel) {
    playerModel.notify_player(this.selling.toString());
  }

  @Override
  public String getName() {
    return this.NAME;
  }

  public String getType() {
    return "seller";
  }

  @Override
  public String toString() {
    return "Seller: " + this.NAME + " in: " + this.LOCATION;
  }

  @Override
  public int compareTo(String location) {
    return this.getLocation().compareTo(location);
  }
}
