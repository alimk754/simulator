package com.example.demo.mementos;

import com.example.demo.classes.Products;
import com.example.demo.classes.Queueing;
import com.example.demo.subscribers.QueueSubscriber;

import java.util.ArrayList;
import java.util.List;

public class QueuesSnapShot {
    private int id;
    List<Products> queue=new ArrayList<>(50);
    QueueSubscriber queueSubscriber;

    public QueueSubscriber getQueueSubscriber() {
        return queueSubscriber;
    }

    public void setQueueSubscriber(QueueSubscriber queueSubscriber) {
        this.queueSubscriber = queueSubscriber;
    }

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
    public QueuesSnapShot(Queueing queue) {
        this.id = queue.getId();
        // Create new list with copied elements
        this.queue = new ArrayList<>(queue.getQueue());
        this.queueSubscriber = queue.getQueueWebSocket();
    }
}
