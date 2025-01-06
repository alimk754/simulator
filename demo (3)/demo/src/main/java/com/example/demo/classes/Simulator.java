package com.example.demo.classes;

import com.example.demo.mementos.MachineSnapShot;
import com.example.demo.mementos.QueuesSnapShot;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulator {
    private Thread monitorThread;
    static Simulator instance = null;
    private int machineId=0;
    private int QueueId=0;
    private List<Queueing> queues=new ArrayList<>();
    private List<Machine> machines=new ArrayList<>();
    private List<Thread> threads=new ArrayList<>();
    public List<Queueing> getQueues() {
        return queues;
    }

    public void setQueues(List<Queueing> queues) {
        this.queues = queues;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }
    //data base replacement functions
    public void addMachine(Machine m){
        machineId++;
        m.setMachineId(machineId);
        machines.add(m);
    }
    public void deleteMachine(Machine m){
        machines.remove(m);
    }
    public void addQueue(Queueing q){
        QueueId++;
        q.setId(QueueId);
        queues.add(q);
    }
    public void deleteQueue(Queueing q){
        queues.remove(q);
    }
    public void deleteMachineById(int id){
        for(Machine machine:machines){
            if(machine.getMachineId()==id){
                machines.remove(machine);
                break;
            }
        }
    }
    public void deleteQueueById(int id){
        for(Queueing q:queues){
            if(q.getId()==id){
                queues.remove(q);
                break;
            }
        }
    }
    public Machine findMachineById(int id){
        for(Machine machine:machines){
            if(machine.getMachineId()==id){
                return machine;
            }
        }
        return null;
    }
    public Queueing findQueueById(int id){
        for(Queueing q:queues){
            if(q.getId()==id){
                return q;
            }
        }
        return null;
    }

    ////////////////////////////////////////
    //singleTone
    public static Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

    // Start the simulation
    public synchronized void start(int size) {
        Queueing mainQueue = queues.get(0);
        for (int i = 0; i < size; i++) {
            Products p = new Products();
            mainQueue.queue.add(p);
        }
        ReplayTracker.takeSnapshot(machines, queues, machineId, QueueId);
        System.out.println(mainQueue.queue.size());
        System.out.println(machines.size());
        System.out.println(queues.size());
        run();
    }

    // Run the simulation
    public synchronized void run() {
        for (Machine machine : machines) {
            Thread thread = new Thread(machine);
            threads.add(thread);
            thread.start();
        }

        // Stop existing monitor thread if running
        if (monitorThread != null && monitorThread.isAlive()) {
            monitorThread.interrupt();
            try {
                monitorThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Start new monitor thread
        monitorThread = new Thread(this::monitorThreads);
        monitorThread.start();
    }

    // Monitor threads to check if all are waiting
    private void monitorThreads() {
        while (true) {
            try {
                Thread.sleep(1000); // Check every second
            } catch (InterruptedException e) {
                System.out.println("Monitor thread interrupted.");
                break;
            }

            boolean allWaiting = true;
            for (Machine machine : machines) {
                if (machine.isWorking()) {
                    allWaiting = false;
                    break;
                }
            }

            if (allWaiting) {
                System.out.println("All threads are waiting. Interrupting all threads...");
                interruptAllThreads();
                break; // Exit the monitor loop
            }
        }
    }

    // Interrupt all threads
    private void interruptAllThreads() {
        for (Thread thread : threads) {
            thread.interrupt(); // Interrupt each thread
        }
    }

    // Delete all threads and reset state
    public synchronized void delete() {
        if (monitorThread != null) {
            monitorThread.interrupt();
        }

        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        threads.clear();
        machines.clear();
        queues.clear();
        machineId = 0;
        QueueId = 0;
        monitorThread = null;
    }

    // Revert to a previous state
    public synchronized void revert() {
        this.delete();
        try {
            Thread.sleep(100);
            ReplayTracker replayTracker = ReplayTracker.getInstance();

            // Restore queues
            for (QueuesSnapShot queueSnapShot : replayTracker.getQueuesSnapShots()) {
                Queueing queue = new Queueing(queueSnapShot);
                queues.add(queue);
            }

            // Restore machines
            for (MachineSnapShot machineSnapShot : replayTracker.getMachineSnapShots()) {
                Machine machine = new Machine(machineSnapShot);
                // No need to clear lists - just add machine
                machines.add(machine);
            }

            this.machineId = replayTracker.getMachineId();
            this.QueueId = replayTracker.getQueueId();
            this.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Revert interrupted: " + e.getMessage());
        }
    }
}
