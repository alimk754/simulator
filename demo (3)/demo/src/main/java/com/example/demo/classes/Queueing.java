package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
@Entity
@Table(name="Queue")
public class Queueing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_id")
    private int id;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "machines")
    List<Machine> machines;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "products")
    BlockingQueue<Products> queue=new ArrayBlockingQueue<>(1);
    public synchronized void send(){

    }
    public synchronized void send(String str) throws InterruptedException {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public BlockingQueue<Products> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Products> queue) {
        this.queue = queue;
    }
}