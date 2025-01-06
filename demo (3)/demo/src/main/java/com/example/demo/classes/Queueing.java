package com.example.demo.classes;

import com.example.demo.mementos.QueuesSnapShot;
import com.example.demo.subscribers.QueueSubscriber;
import com.example.demo.controllers.QueueWebSocket;

import java.util.ArrayList;
import java.util.List;

public class Queueing {
    private QueueSubscriber queueWebSocket; //programming to interface
    private int id;
    List<Products> queue=new ArrayList<>(50);
    //geeters and setters
    public List<Products> getQueue() {
        return queue;
    }

    public QueueSubscriber getQueueWebSocket() {
        return queueWebSocket;
    }

    public void setQueue(List<Products> queue) {
        this.queue = queue;
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
    //synchronized functions
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
    //constructors
    Queueing(QueuesSnapShot queuesSnapShot){
        this.queueWebSocket=queuesSnapShot.getQueueSubscriber();
        this.queue=new ArrayList<>(queuesSnapShot.getQueue());
        this.id=queuesSnapShot.getId();
    }
    public Queueing (int capacity){
        queue=new ArrayList<>(capacity);
    }

    @Override
    public String toString() {
        return "Queueing{" +
                "queueWebSocket=" + queueWebSocket +
                ", id=" + id +
                ", queue=" + queue +
                '}';
    }
}