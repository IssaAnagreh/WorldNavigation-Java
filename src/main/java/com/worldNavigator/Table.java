package com.worldNavigator;

import org.json.simple.JSONObject;

public class Table extends Item {
    private final String LOCATION;
    private final String NAME = "Table";

    public Table(JSONObject table) {
        this.LOCATION = (String) table.get("location");
        if (table.get("existed").equals("true")) {
            super.setCheckBehavior(new Unlocked_Checkable(table, this.LOCATION));
        }
    }

    public String getLocation() {
        return this.LOCATION;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "table";
    }

    @Override
    public int compareTo(String location) {
        return this.getLocation().compareTo(location);
    }

    public boolean equals(Table table) {
        if (table != null) {
            return table.LOCATION.equals(this.LOCATION);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.LOCATION.hashCode();
    }

    @Override
    public String toString() {
        return "Table: " + this.NAME + " in: " + this.LOCATION;
    }
}
