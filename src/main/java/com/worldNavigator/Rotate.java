package com.worldNavigator;

public class Rotate {
    String orientation;

    public Rotate(String orientation) {
        this.orientation = orientation;
    }

    public String left() {
        switch (this.orientation) {
            case "n":
                this.orientation = "w";
                break;
            case "e":
                this.orientation = "n";
                break;
            case "s":
                this.orientation = "e";
                break;
            case "w":
                this.orientation = "s";
                break;
            default:
                System.out.println("Semething went wrong while rotating");
                break;
        }
        System.out.println(this.orientation);
        return this.orientation;
    }

    public String right() {
        switch (this.orientation) {
            case "n":
                this.orientation = "e";
                break;
            case "e":
                this.orientation = "s";
                break;
            case "s":
                this.orientation = "w";
                break;
            case "w":
                this.orientation = "n";
                break;
            default:
                System.out.println("Semething went wrong while rotating");
                break;
        }
        System.out.println(this.orientation);
        return this.orientation;
    }
}
