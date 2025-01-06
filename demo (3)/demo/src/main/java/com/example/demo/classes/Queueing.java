package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Queueing {

    private int id;
    List<Products> queue=new ArrayList<>(50);
    public synchronized void add(Products p){
        queue.addFirst(p);
        notifyAll();
    }
    public synchronized Products remove() throws InterruptedException {
        if(queue.isEmpty())
            wait();
        notifyAll();
        return queue.removeLast();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Queueing (int capacity){
        queue=new ArrayList<>(capacity);
    }
}