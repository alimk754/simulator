package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.Random;

@Entity
@Table(name="product")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private int id;
    @Column(name="color")
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
