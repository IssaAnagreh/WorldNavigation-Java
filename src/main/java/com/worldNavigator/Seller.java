package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Seller extends Item {
    public String LOCATION;
    public final String NAME = "Seller";
    HashMap<String, Integer> selling = new HashMap<>();
    public HashMap<String, ArrayList> contents;

    public Seller(JSONObject seller) {
        this.LOCATION = (String) seller.get("location");
        if (!seller.get("existed").equals("true")) {
            super.setCheckBehavior(new Checkable(seller, this.LOCATION, super.useKeyBehavior));
        }
        if (seller.get("selling") != null) {
            JSONObject temp_selling = (JSONObject) seller.get("selling");
            for (Object item_name : temp_selling.keySet()) {
                this.selling.put(item_name.toString(), Integer.parseInt(temp_selling.get(item_name).toString()));
            }
        }
    }

    public String getLocation() {
        return this.LOCATION;
    }

    public HashMap check_content(String location) {
        HashMap content = new HashMap<>();
        if (location.equals(this.LOCATION)) {
            content = this.contents;
        }
        return content;
    }

    public HashMap buy(int gold) {
        HashMap output = new HashMap<>();

        int counter = 0;
        List categories = new ArrayList();
        System.out.println("Items ready to be sold:");
        System.out.println(-1 + ": " + "Quit");
        System.out.println(this.contents.toString());
        for (Object category : this.contents.keySet()) {
            for (Object item : this.contents.get(category)) {
                System.out.println(counter + ": " + category + " " + item);
                categories.add(category);
                categories.add(item);
                counter++;
            }
        }

        System.out.println("Type the number of the item you want to buy");
        Scanner sc = new Scanner(System.in);
        int index = sc.nextInt();
        if (index != -1) {
            if (categories.size() > 0) {
                String category_name;
                if (categories.size() > (index * 2) && index >= 0) {
                    category_name = categories.get(index * 2).toString();
                    output.put("kind", category_name);
                } else {
                    output.put("kind", "out of bounds");
                    return output;
                }

                Object category = this.contents.get(category_name);
                if (category != null) {
                    HashMap item = (HashMap) categories.get((index * 2) + 1);
                    if (item != null) {
                        for (Object item_name : item.keySet()) {
                            output.put("item", item.get("name"));
                            if (gold - Integer.parseInt(item.get(item_name).toString()) >= 0) {
                                output.put("golds", gold - Integer.parseInt(item.get(item_name).toString()));
                                return output;
                            } else {
                                System.out.println("Return when you have enough gold");
                                return new HashMap<>();
                            }
                        }
                    } else {
                        System.out.println("This index is not available");
                        return new HashMap<>();
                    }
                } else {
                    System.out.println("This category is not available");
                    return new HashMap<>();
                }
            } else {
                System.out.println("Seller has nothing to sell");
                return new HashMap<>();
            }
        } else {
            System.out.println("You quited trading");
            return output;
        }
        return output;
    }

    public int sell(int golds, String type) {
        for (String category : this.selling.keySet()) {
            System.out.println(category);
        }

        int output = golds;
        if (this.selling.get(type) != null) {
            output = golds + this.selling.get(type);
        } else {
            System.out.println("This item is not in the list");
        }

        return output;

    }

    public void sellingList() {
        System.out.println(this.selling);
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "seller";
    }

    @Override
    public String getDetails() {
        return this.NAME + " in " + this.LOCATION;
    }

    @Override
    public String toString() {
        return "Seller, Location: " + this.LOCATION;
    }
}
