package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.Random;


public class Products {

    private int id;
    private int color;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    Products(){
        Random rand=new Random();
        this.color= rand.nextInt(7)+1;
    }
}
