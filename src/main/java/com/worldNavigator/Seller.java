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
  private List categories;
  private PlayerModel playerModel;

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

  public Map check_content() {
    return this.contents.getContents();
  }

  public void buy(int gold, PlayerModel playerModel) {
    this.playerModel = playerModel;
    buyingCommands(gold);
  }

  private void buyingCommands(int gold) {
    this.playerModel.notify_player("Type the number of the item you want to buy");
    this.categories = this.buyingList(this.playerModel);
    Scanner sc = new Scanner(System.in);
    int index = sc.nextInt();
    if (index == -1) {
      this.quitBuying(this.playerModel);
    } else {
      this.completeBuying(gold, index);
    }
  }

  private void completeBuying(int gold, int index) {
    if (this.categories.isEmpty()) {
      this.playerModel.notify_player("Seller has nothing to sell");
    } else {
      String category_name;
      if (this.categories.size() > (index * 2) && index >= 0) {
        category_name = this.categories.get(index * 2).toString();
      } else {
        this.playerModel.notify_player("Choose an existed item's index");
        this.buy(gold, this.playerModel);
        return;
      }
      controlPlayerContents(category_name, index, gold);
    }
  }

  private void controlPlayerContents(String category_name, int index, int gold) {
    Object category = this.contents.getContents().get(category_name);
    if (category == null) {
      this.playerModel.notify_player("This category is not available");
    } else {
      HashMap item = (HashMap) this.categories.get((index * 2) + 1);
      if (item == null) {
        this.playerModel.notify_player("This index is not available");
      } else {
        if (gold - Integer.parseInt(item.get("cost").toString()) >= 0) {
          if ("keys".equals(category_name)) {
            List<Key> list = (List<Key>) this.playerModel.getContent("keys");
            list.add((Key) item.get("name"));
            this.playerModel.addToContents("keys", list);
          } else {
            this.playerModel.addToContents(
                category_name, ((int) this.playerModel.getContent(category_name)) + 1);
          }
          this.playerModel.notify_player("Bought " + item.get("name"));
          this.playerModel.addToContents(
              "golds", gold - Integer.parseInt(item.get("cost").toString()));
        } else {
          this.playerModel.notify_player("Return when you have enough gold");
        }
      }
    }
  }

  private void quitBuying(PlayerModel playerModel) {
    playerModel.notify_player("You quited trading");
  }

  private List buyingList(PlayerModel playerModel) {
    int counter = 0;
    List categories = new ArrayList();
    playerModel.notify_player(-1 + ": " + "quit");
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

  public void sell(PlayerModel playerModel) {
    this.playerModel = playerModel;

    this.playerModel.notify_player("Items and values this seller is willing to buy: ");
    this.playerModel.notify_player(this.selling.toString());

    this.playerModel.notify_player(
        "Enter the type of the item you want to sell: "
            + this.selling.keySet()
            + " or type quit to cancel");
    Scanner sc1 = new Scanner(System.in);
    String type = sc1.next();
    this.sellingCommands(type);
  }

  private void sellingCommands(String type) {
    if (this.selling.get(type) == null) {
      playerModel.notify_player("This item is not in the list");
      this.sell(playerModel);
    } else {
      if (type.equals("quit")) {
        return;
      } else {
        this.checkSellingType(type);
      }
    }
  }

  private void checkSellingType(String type) {
    if (type.equals("keys")) {
      this.sellingKeys(type);
    } else {
      if (this.playerModel.getContent(type) == null) {
        this.playerModel.notify_player("Choose a correct type");
        this.sell(playerModel);
      } else {
        if (((int) this.playerModel.getContent(type)) > 0) {
          this.playerModel.addToContents(type, ((int) this.playerModel.getContent(type)) - 1);
          this.playerModel.addToContents(
              "golds",
              (Integer.parseInt(this.playerModel.getContent("golds").toString()))
                  + this.selling.get(type));
          this.playerModel.notify_player("Done!");
          this.playerModel.notify_player("Your Items: ");
          this.playerModel.myItems();
        } else {
          this.playerModel.notify_player("You dont have " + type + " to sell");
          this.sell(playerModel);
        }
      }
    }
  }

  private void sellingKeys(String type) {
    if (((List<KeyChecker>) this.playerModel.getContent("keys")).isEmpty()) {
      this.playerModel.notify_player("You dont have keys to sell");
      this.sell(playerModel);
    } else {
      this.playerModel.notify_player(
          "Enter the name of the item you want to sell: " + this.playerModel.getContent("keys"));
      Scanner sc2 = new Scanner(System.in);
      String item = sc2.next();
      for (KeyChecker key : ((List<KeyChecker>) this.playerModel.getContent("keys"))) {
        if (key.toString().equals(item)) {
          ((List<KeyChecker>) this.playerModel.getContent("keys")).remove(key);
          this.playerModel.addToContents(
              "golds",
              (Integer.parseInt(this.playerModel.getContent("golds").toString()))
                  + this.selling.get(type));
          this.playerModel.notify_player("Done!");
          this.playerModel.notify_player("Your Items: ");
          this.playerModel.myItems();
          return;
        }
      }
    }
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
