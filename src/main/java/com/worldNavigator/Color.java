package com.worldNavigator;

import java.util.Random;

public class Color {
    public String color;
    public Color() {
        Random random = new Random();
        int random_num = random.nextInt(10);

        switch(random_num) {
            case 9:
                color = "black";
                break;
            case 8:
                color = "white";
                break;
            case 7:
                color = "red";
                break;
            case 6:
                color = "green";
                break;
            case 5:
                color = "blue";
                break;
            case 4:
                color = "yellow";
                break;
            case 3:
                color = "grey";
                break;
            case 2:
                color = "pink";
                break;
            case 1:
                color = "maroon";
                break;
            case 0:
                color = "violet";
                break;
            default:
                color = "brown";
        }
    }

    @Override
    public String toString() {
        return color;
    }
}
