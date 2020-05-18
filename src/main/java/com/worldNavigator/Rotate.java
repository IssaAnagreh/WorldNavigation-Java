package com.worldNavigator;

public class Rotate {
    String ORIENTATION;
    PlayerModel playerModel;

    public Rotate(String orientation, PlayerModel playerModel) {
        this.ORIENTATION = orientation;
        this.playerModel = playerModel;
    }

    public String left() {
        switch (this.ORIENTATION) {
            case "n":
                this.ORIENTATION = "w";
                break;
            case "e":
                this.ORIENTATION = "n";
                break;
            case "s":
                this.ORIENTATION = "e";
                break;
            case "w":
                this.ORIENTATION = "s";
                break;
            default:
                this.playerModel.notify_player("Semething went wrong while rotating");
                break;
        }
        this.playerModel.notify_player(this.ORIENTATION);
        return this.ORIENTATION;
    }

    public String right() {
        switch (this.ORIENTATION) {
            case "n":
                this.ORIENTATION = "e";
                break;
            case "e":
                this.ORIENTATION = "s";
                break;
            case "s":
                this.ORIENTATION = "w";
                break;
            case "w":
                this.ORIENTATION = "n";
                break;
            default:
                this.playerModel.notify_player("Semething went wrong while rotating");
                break;
        }
        this.playerModel.notify_player(this.ORIENTATION);
        return this.ORIENTATION;
    }

    @Override
    public String toString() {
        return "Rotate";
    }
}
