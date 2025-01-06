package com.example.demo.classes;

import com.example.demo.subscribers.QueueSubscriber;
import com.example.demo.controllers.QueueWebSocket;

import java.util.ArrayList;
import java.util.List;

public class Queueing {
    private QueueSubscriber queueWebSocket; //programming to interface
    private int id;
    List<Products> queue=new ArrayList<>(50);

    public List<Products> getQueue() {
        return queue;
    }

    public void setQueue(List<Products> queue) {
        this.queue = queue;
    }

    public synchronized void add(Products p){
        queue.addFirst(p);
        queueWebSocket.updateQueue(this);
        notifyAll();
    }
    public synchronized Products remove() throws InterruptedException {
        if(queue.isEmpty())
            wait();
        notifyAll();
        Products p=queue.removeLast();
        queueWebSocket.updateQueue(this);
        return p;

    }
    public void setQueueWebSocket(QueueWebSocket queueWebSocket) {
        this.queueWebSocket = queueWebSocket;
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