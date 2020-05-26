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
    return buyingCommands(gold, playerModel);
  }

  public Map buyingCommands(int gold, PlayerModel playerModel) {
    playerModel.notify_player("Type the number of the item you want to buy");
    this.buyingList(playerModel);
    Scanner sc = new Scanner(System.in);
    int index = sc.nextInt();
    if (index == -1) {
      return this.quitBuying(playerModel);
    } else {
      return this.completeBuying(gold, playerModel, index);
    }
  }

  private Map completeBuying(int gold, PlayerModel playerModel, int index) {
    Map output = new HashMap<>();
    List categories = this.buyingList(playerModel);

    if (categories.isEmpty()) {
      playerModel.notify_player("Seller has nothing to sell");
      return new HashMap<>();
    } else {
      String category_name;
      if (categories.size() > (index * 2) && index >= 0) {
        category_name = categories.get(index * 2).toString();
        output.put("kind", category_name);
      } else {
        output.put("kind", "out of bounds");
        return output;
      }

      Object category = this.contents.getContents().get(category_name);
      if (category == null) {
        playerModel.notify_player("This category is not available");
        return new HashMap<>();
      } else {
        HashMap item = (HashMap) categories.get((index * 2) + 1);
        if (item == null) {
          playerModel.notify_player("This index is not available");
          return new HashMap<>();
        } else {
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
        }
      }
    }
    return output;
  }

  private Map quitBuying(PlayerModel playerModel) {
    playerModel.notify_player("You quited trading");
    return new HashMap<>();
  }

  public int sell(int golds, String type, PlayerModel playerModel) {
    int output = golds;
    if (this.selling.get(type) == null) {
      playerModel.notify_player("This item is not in the list");
    } else {
      output = golds + this.selling.get(type);
    }

    return output;
  }

  public void sellingList(PlayerModel playerModel) {
    playerModel.notify_player(this.selling.toString());
  }

  public List buyingList(PlayerModel playerModel) {
    int counter = 0;
    List categories = new ArrayList();
    playerModel.notify_player(-1 + ": " + "Quit");
    for (Object category : this.contents.getContents().keySet()) {
      for (Object item : ((List) this.contents.getContents().get(category))) {
        playerModel.notify_player(counter + ": " + category + " " + item);
        categories.add(category);
        categories.add(item);
        counter++;
      }
    }
    return categories;
  }

  @Override
  public String getName() {
    return this.NAME;
  }

  public String getType() {
    return "seller";
  }

  @Override
  public int compareTo(String location) {
    return this.getLocation().compareTo(location);
  }

  @Override
  public String toString() {
    return "Seller: " + this.NAME + " in: " + this.LOCATION;
  }
}
