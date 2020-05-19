package com.worldNavigator;

import org.json.simple.JSONObject;

public class Gate extends Item implements NextGoing {
    public final String NAME;
    private final Boolean IS_GOLDEN;
    private final String NEXT_ROOM;
    private final String LOCATION;

    public Gate(JSONObject gate) {
        this.NAME = (String) gate.get("name");
        this.LOCATION = (String) gate.get("location");
        this.IS_GOLDEN = gate.get("golden").equals("true");
        this.NEXT_ROOM = (String) gate.get("to");

        if (gate.get("key") != null) {
            super.setUseKeyBehavior(new Openable(gate, "Gate"));
        }
        super.setCheckBehavior(new Uncheckable());
    }

    @Override
    public String getNextRoom() {
        if (super.useKeyBehavior != null && super.useKeyBehavior.getIs_locked()) {
            return "locked";
        } else {
            if (this.getGolden() != null && this.getGolden()) {
                return "golden";
            }
            return this.NEXT_ROOM;
        }
    }

    private Boolean getGolden() {
        return this.IS_GOLDEN;
    }

    @Override
    public String getLocation() {
        return this.LOCATION;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    @Override
    public String getType() {
        return "gate";
    }

    @Override
    public String toString() {
        return "Gate: " + this.NAME + ", in " + this.LOCATION;
    }

    @Override
    public int compareTo(String location) {
        return this.getLocation().compareTo(location);
    }
}
