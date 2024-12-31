package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
@Entity
@Table(name="Machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Machine_id")
    int id;
    @Column(name = "table")
    int seconds;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "queues")
    List<Queueing> Sending;
    @Column(name="working")
    boolean isWorking=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public synchronized void put(String str)  throws InterruptedException {

    }
    public synchronized void send(String str) throws InterruptedException {

    }
    void addSending(Queueing q){
        Sending.add(q);
    }

    void removeSending(Queueing q){
        Sending.remove(q);
    }
    public Machine(){
        Random random = new Random();
        seconds = random.nextInt(21) + 5;
    }
}
