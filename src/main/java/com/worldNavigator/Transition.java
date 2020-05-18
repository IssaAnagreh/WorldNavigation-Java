package com.worldNavigator;

public class Transition {
    private String new_location;
    private PlayerModel playerModel;

    public Transition(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void move(String location, String orientation, PlayerControllerMaster.MoveParam move) {
        if (move == PlayerControllerMaster.MoveParam.forward) {
            forward(location, orientation);
        } else if (move == PlayerControllerMaster.MoveParam.backward) {
            backward(location, orientation);
        } else {
            this.playerModel.notify_player("wrong movement input, user 'forward' or 'backward'");
        }
    }

    public void openNextRoom(String location, String orientation, PlayerControllerMaster.MoveParam move) {
        if (move == PlayerControllerMaster.MoveParam.forward) {
            forward_nextRoom(location, orientation);
        }
    }

    private void forward(String location, String orientation) {
        switch (orientation) {
            case "n": {
                int number = Integer.parseInt(location.substring(location.length() - 1));
                this.new_location = number > 1 ? (location.charAt(0)) + "" + (number - 1) : (location.charAt(0)) + "" + number;
                break;
            }
            case "e":
                this.new_location = nextChar(location.charAt(0) + "") + ((location.charAt(1)) + "");
                break;
            case "s": {
                int number = Integer.parseInt(location.substring(location.length() - 1));
                this.new_location = number < 5 ? (location.charAt(0)) + "" + (number + 1) : (location.charAt(0)) + "" + number;
                break;
            }
            default:
                this.new_location = prevChar(location.charAt(0) + "") + ((location.charAt(1)) + "");
                break;
        }
    }

    private void backward(String location, String orientation) {
        switch (orientation) {
            case "n": {
                int number = Integer.parseInt(location.substring(location.length() - 1));
                this.new_location = number < 5 ? (location.charAt(0)) + "" + (number + 1) : (location.charAt(0)) + "" + number;
                break;
            }
            case "e":
                this.new_location = prevChar(location.charAt(0) + "") + ((location.charAt(1)) + "");
                break;
            case "s": {
                int number = Integer.parseInt(location.substring(location.length() - 1));
                this.new_location = number > 1 ? (location.charAt(0)) + "" + (number - 1) : (location.charAt(0)) + "" + number;
                break;
            }
            default:
                this.new_location = nextChar(location.charAt(0) + "") + ((location.charAt(1)) + "");
                break;
        }

    }

    private void forward_nextRoom(String location, String orientation) {
        switch (orientation) {
            case "n": {
                int number = Integer.parseInt(location.substring(location.length() - 1));
                this.new_location = number > 1 ? (location.charAt(0)) + "" + (number - 1) : (location.charAt(0)) + "" + 5;
                break;
            }
            case "e":
                this.new_location = nextChar_nextRoom(location.charAt(0) + "") + ((location.charAt(1)) + "");
                break;
            case "s": {
                int number = Integer.parseInt(location.substring(location.length() - 1));
                this.new_location = number < 5 ? (location.charAt(0)) + "" + (number + 1) : (location.charAt(0)) + "" + 1;
                break;
            }
            default:
                this.new_location = prevChar_nextRoom(location.charAt(0) + "") + ((location.charAt(1)) + "");
                break;
        }
    }

    private String nextChar(String c) {
        switch (c) {
            case "a":
                return "b";
            case "b":
                return "c";
            case "c":
                return "d";
            default:
                return "e";
        }
    }

    private String prevChar(String c) {
        switch (c) {
            case "e":
                return "d";
            case "d":
                return "c";
            case "c":
                return "b";
            default:
                return "a";
        }
    }

    private String nextChar_nextRoom(String c) {
        switch (c) {
            case "a":
                return "b";
            case "b":
                return "c";
            case "c":
                return "d";
            case "d":
                return "e";
            default:
                return "a";
        }
    }

    private String prevChar_nextRoom(String c) {
        switch (c) {
            case "e":
                return "d";
            case "d":
                return "c";
            case "c":
                return "b";
            case "b":
                return "a";
            default:
                return "e";
        }
    }

    @Override
    public String toString() {
        return new_location;
    }
}
