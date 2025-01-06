package com.example.demo.mementos;

import com.example.demo.classes.Products;
import com.example.demo.classes.Queueing;

import java.util.ArrayList;
import java.util.List;

public class QueuesSnapShot {
    private int id;
    List<Products> queue=new ArrayList<>(50);

    public List<Products> getQueue() {
        return queue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQueue(List<Products> queue) {
        this.queue = queue;
    }
    QueuesSnapShot(Queueing queue){
        this.id=queue.getId();
        this.queue=queue.getQueue();
    }
}
