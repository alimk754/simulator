package com.example.demo.classes;

import jakarta.persistence.*;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Machine implements Runnable{
    private int MachineId;
    private int color=8;
    int seconds=3;
    List<Queueing> Sending=new ArrayList<>();
    List<Queueing> requesting=new ArrayList<>();
    boolean isWorking = false;

    // Getters and Setters

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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

    @Override
    public void run() {
        Random random = new Random();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Check if the machine is working
                synchronized (this) {
                    if (isWorking) {
                        System.out.println("Machine " + getMachineId() + " is waiting (isWorking=true)");
                        wait(); // Wait while holding the lock
                    }
                }

                // Find non-empty sending queues
                List<Queueing> nonEmptySendingQueues = Sending.stream()
                        .filter(queue -> queue != null && !queue.queue.isEmpty())
                        .toList();

                while (nonEmptySendingQueues.isEmpty()) {
                    nonEmptySendingQueues = Sending.stream()
                            .filter(queue -> queue != null && !queue.queue.isEmpty())
                            .toList();
                    System.out.println("Machine " + getMachineId() + " is waiting (no non-empty queues)");
                }

                // Consume a product from a random non-empty queue
                Products product = null;
                int randomQueue = random.nextInt(nonEmptySendingQueues.size());
                Queueing q1 = nonEmptySendingQueues.get(randomQueue);

                synchronized (this) {
                    try {
                        product = q1.remove();
                        System.out.println("Machine " + getMachineId() + " is consuming product with color " + product.getColor());
                    } catch (Exception e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        System.err.println("Error removing product: " + e.getMessage());
                        return; // Exit the run method if interrupted
                    }
                    this.color = product.getColor();
                    this.isWorking = true; // Mark the machine as working
                }

                // Simulate processing time
                try {
                    System.out.println("Machine " + getMachineId() + " is sleeping for " + seconds + " seconds");
                    Thread.sleep(seconds * 1000); // Sleep for the specified duration
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    System.err.println("Thread interrupted: " + e.getMessage());
                    return; // Exit the run method if interrupted
                }

                // Add the product to a random requesting queue
                System.out.println("Machine " + getMachineId() + " is finished");
                randomQueue = random.nextInt(requesting.size());
                requesting.get(randomQueue).add(product);

                // Mark the machine as not working and notify other threads
                synchronized (this) {
                    this.isWorking = false; // Mark the machine as not working
                    notifyAll(); // Notify waiting threads
                    System.out.println("Machine " + getMachineId() + " is notifying other threads");
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                System.err.println("Thread interrupted: " + e.getMessage());
                return; // Exit the run method if interrupted
            } catch (Exception e) {
                System.err.println("Error in machine " + getMachineId() + ": " + e.getMessage());
                e.printStackTrace();
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

    public Machine() {
        Random random = new Random();
        seconds = random.nextInt(21) + 5;
    }
    //Builder
    public Machine(int color){
        Random random = new Random();
        seconds = random.nextInt(21) + 5;
        this.color=color;
    }
}