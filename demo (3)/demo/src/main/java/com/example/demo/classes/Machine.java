package com.example.demo.classes;

import com.example.demo.mementos.MachineSnapShot;
import com.example.demo.subscribers.MachineSubscriber;
import com.example.demo.controllers.WebSocketController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Machine implements Runnable{
    private MachineSubscriber webSocketController;
    private int MachineId;
    private String color;
    private final String ogcolor="#000000";
    int seconds=3;
    List<Queueing> Sending=new ArrayList<>();
    List<Queueing> requesting=new ArrayList<>();
    boolean isWorking = false;

    // Getters and Setters

    public void setWebSocketController(WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    public List<Queueing> getRequesting() {
        return requesting;
    }

    public void setRequesting(List<Queueing> requesting) {
        this.requesting = requesting;
    }

    public int getMachineId() {
        return MachineId;
    }

    public void setMachineId(int machineId) {
        MachineId = machineId;
    }

    public int getSeconds() {
        return seconds;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Queueing> getSending() {
        return Sending;
    }

    public void setSending(List<Queueing> sending) {
        Sending = sending;
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
    public MachineSubscriber getWebSocketController() {
        return webSocketController;
    }
    //method run
    @Override
    public void run() {
        Random random = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (this) {
                    while (isWorking) {
                        System.out.println("Machine " + getMachineId() + " is waiting (isWorking=true)");
                        wait(); // Wait while holding the lock
                    }
                }

                List<Queueing> nonEmptySendingQueues = Sending.stream()
                        .filter(queue -> queue != null && !queue.queue.isEmpty())
                        .toList();

                synchronized (this) {
                    while (nonEmptySendingQueues.isEmpty()) {
                        System.out.println("Machine " + getMachineId() + " is waiting (no non-empty queues)");
                        Thread.sleep(1000);
                        nonEmptySendingQueues = Sending.stream()
                                .filter(queue -> queue != null && !queue.queue.isEmpty())
                                .toList();
                    }
                }

                Products product = null;
                int randomQueue = random.nextInt(nonEmptySendingQueues.size());
                Queueing q1 = nonEmptySendingQueues.get(randomQueue);

                synchronized (this) {
                    try {
                        product = q1.remove();
                        System.out.println("Machine " + getMachineId() + " is consuming product with color " + product.getColor());
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Error removing product: " + e.getMessage());
                        return;
                    }
                    this.color = product.getColor();
                    webSocketController.updateMachineState(this);
                    this.isWorking = true;
                }

                // Simulate processing time
                try {
                    System.out.println("Machine " + getMachineId() + " is sleeping for " + seconds + " seconds");
                    Thread.sleep(seconds * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread interrupted: " + e.getMessage());
                    return;
                }

                // Add the product to a random requesting queue
                System.out.println("Machine " + getMachineId() + " is finished");
                if (!requesting.isEmpty()) {
                    randomQueue = random.nextInt(requesting.size());
                    Queueing targetQueue = requesting.get(randomQueue);
                    if (targetQueue != null) {
                        targetQueue.add(product);
                    } else {
                        System.err.println("Target queue is null!");
                    }
                } else {
                    System.err.println("Requesting list is empty!");
                }

                synchronized (this) {
                    this.isWorking = false;
                    notifyAll();
                    this.color = ogcolor;
                    System.out.println("Machine " + getMachineId() + " is notifying other threads");
                    webSocketController.updateMachineState(this);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
                return;
            } catch (Exception e) {
                System.err.println("Error in machine " + getMachineId() + ": " + e.getMessage());
            }
        }
    }
    public void addSending(Queueing q) {
        Sending.add(q);
    }

    public void removeSending(Queueing q) {
        Sending.remove(q);
    }
    public void addrequesting(Queueing q) {
        requesting.add(q);
    }

    public void removerequesting(Queueing q) {
        requesting.remove(q);
    }

    //constructors
    public Machine(){
        Random random = new Random();
        seconds = random.nextInt(21) + 5;
        this.color="#000000";
    }
    public Machine(MachineSnapShot machineSnapShot) {
        this.isWorking = false; // Start fresh
        this.color = machineSnapShot.getColor();
        this.requesting = new ArrayList<>(machineSnapShot.getRequesting());
        this.Sending = new ArrayList<>(machineSnapShot.getSending());
        this.seconds = machineSnapShot.getSeconds();
        this.MachineId = machineSnapShot.getMachineId();
        this.webSocketController = machineSnapShot.getMachineSubscriber();
    }
}