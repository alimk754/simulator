package com.example.demo.dto;

import com.example.demo.classes.Products;

import java.util.ArrayList;
import java.util.List;

public class QueueDto {
    public int capacity;
    public int id;
    public List<Products> products=new ArrayList<>();

    public int getCapacity() {
        return capacity;
    }

    public int getId() {
        return id;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
