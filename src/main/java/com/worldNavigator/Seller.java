package com.worldNavigator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Seller implements ContainerItems, Item {
    public String location;
    public String name = "Seller";
    public List<HashMap> keys = new ArrayList<>();
    public List<HashMap> flashLights = new ArrayList<>();
    HashMap<String, List> contents = new HashMap<>();
    HashMap<String, Integer> selling = new HashMap<>();

    public Seller(JSONObject seller) {
        if (seller.get("existed").equals("true")) {
            this.location = (String) seller.get("location");
            JSONObject content = (JSONObject) seller.get("content");
            if (content.get("keys") != null) {
                JSONArray temp_keys = (JSONArray) content.get("keys");
                if (temp_keys != null) temp_keys.forEach(emp -> {
                    JSONObject key = (JSONObject) emp;
                    HashMap<Key, String> selling_key = new HashMap<>();
                    selling_key.put(new Key(key.get("name").toString()), key.get("cost").toString());
                    this.keys.add(selling_key);
                });
            }
            if (content.get("flashLights") != null) {
                JSONArray temp_flashLights = (JSONArray) content.get("flashLights");
                if (temp_flashLights != null) temp_flashLights.forEach(emp -> {
                    JSONObject flashLight = (JSONObject) emp;
                    HashMap<String, String> selling_flashLight = new HashMap<>();
                    selling_flashLight.put(flashLight.get("#").toString(), flashLight.get("cost").toString());
                    this.flashLights.add(selling_flashLight);
                });
            }
        }
        if (seller.get("selling") != null) {
            JSONObject temp_selling = (JSONObject) seller.get("selling");
            for(Object item_name: temp_selling.keySet()){
                this.selling.put(item_name.toString(), Integer.parseInt(temp_selling.get(item_name).toString()));
            }
        }

        contents.put("Keys", this.keys);
        contents.put("FlashLights", this.flashLights);
    }

    public String getLocation() {
        return location;
    }

    public HashMap check_content(String location) {
        HashMap<String, List> content = new HashMap<>();
        if (location.equals(this.location)) {
            content = this.contents;
        } else {
            System.out.println("You must be in the same location of this seller");
        }
        return content;
    }

    public HashMap buy(int gold) {
        HashMap output = new HashMap<>();

        int counter = 0;
        List categories = new ArrayList();
        System.out.println("Items ready to be sold:");
        System.out.println(-1 + ": " + "Quit");
        for (String category: this.contents.keySet()) {
            for (Object item: this.contents.get(category)) {
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

                List category = this.contents.get(category_name);
                if (category != null) {
                    HashMap item = (HashMap) categories.get((index * 2) + 1);
                    if (item != null) {
                        for (Object item_name : item.keySet()) {
                            output.put("item", item_name);
                            if (gold - Integer.parseInt((String) item.get(item_name)) >= 0) {
                                output.put("golds", gold - Integer.parseInt((String) item.get(item_name)));
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
        System.out.println("Types: ");
        for(String category: this.selling.keySet()) {
            System.out.println(category);
        }

        int output = golds;
        if (this.selling.get(type) != null) {
            output = golds+this.selling.get(type);
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
        return name;
    }

    @Override
    public String getDetails() {
        return name + " in " + location;
    }

    @Override
    public String toString() {
        return "Seller, Location: " + this.location;
    }
}
