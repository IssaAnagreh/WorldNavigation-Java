package com.worldNavigator;

public class Rotate {
    String ORIENTATION;

    public Rotate(String orientation) {
        this.ORIENTATION = orientation;
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
                System.out.println("Semething went wrong while rotating");
                break;
        }
        System.out.println(this.ORIENTATION);
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
                System.out.println("Semething went wrong while rotating");
                break;
        }
        System.out.println(this.ORIENTATION);
        return this.ORIENTATION;
    }

    @Override
    public String toString() {
        return "Rotate";
    }
}
