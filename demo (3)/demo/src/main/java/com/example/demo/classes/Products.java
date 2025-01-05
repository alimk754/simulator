package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.Random;


public class Products {

    private int id;
    private String color;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public static String generateRandomHexColor() {
        Random random = new Random();
        StringBuilder hexColor = new StringBuilder("#");

        // Generate 6 random hex digits (for RRGGBB)
        for (int i = 0; i < 6; i++) {
            int randomDigit = random.nextInt(16);
            hexColor.append(Integer.toHexString(randomDigit));
        }

        return hexColor.toString();
    }
    Products(){
        Random rand=new Random();
        color=Products.generateRandomHexColor();

    }
}
