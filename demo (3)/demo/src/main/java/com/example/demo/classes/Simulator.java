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

    public Thread getMonitorThread() {
        return monitorThread;
    }

    public void setMonitorThread(Thread monitorThread) {
        this.monitorThread = monitorThread;
    }

    public static void setInstance(Simulator instance) {
        Simulator.instance = instance;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getQueueId() {
        return QueueId;
    }

    public void setQueueId(int queueId) {
        QueueId = queueId;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
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
        queues.get(queues.size() - 1).queue.clear();
        queues.get(queues.size() - 1).getQueueWebSocket().updateQueue(queues.get(queues.size() - 1));
        Queueing mainQueue = queues.get(0);
        for (int i = 0; i < size; i++) {
            Products p = new Products();
            mainQueue.queue.add(p);
        }
        ReplayTracker.takeSnapshot(this);
        System.out.println(mainQueue.queue.size());
        System.out.println(machines.size());
        System.out.println(queues.size());
        run();
    }

    // Run the simulation
    public synchronized void run() {
        System.out.println(queues.get(0).queue.size());
        System.out.println(machines.size());
        System.out.println(queues.size());
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

        // Wait for all threads to terminate
        for (Thread thread : threads) {
            try {
                thread.join(); // Wait for the thread to terminate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Clear the list of threads
        threads.clear();
    }

    // Delete all threads and reset state
    public synchronized void delete() {
        interruptAllThreads(); // Interrupt and wait for all threads to terminate
        machines.clear();
        queues.clear();
        machineId = 0;
        QueueId = 0;

        // Stop the monitor thread
        if (monitorThread != null) {

            monitorThread = null;
        }
    }

    // Revert to a previous state
    public synchronized void revert() {
        System.out.println("Fff");
        // Delete current state
        this.delete();
        System.out.println("Dddd");

        // Restore state from ReplayTracker
        ReplayTracker replayTracker = ReplayTracker.getInstance();
        for (QueuesSnapShot queueSnapShot : replayTracker.getQueuesSnapShots()) {
            Queueing queue = new Queueing(queueSnapShot);
            queues.add(queue);
            queue.getQueueWebSocket().updateQueue(queue);
        }
        for (MachineSnapShot machineSnapShot : replayTracker.getMachineSnapShots()) {
            Machine machine = new Machine(machineSnapShot);
            // For machine.Sending
            List<Queueing> sendingCopy = new ArrayList<>(machine.Sending);
            for (Queueing queueing : sendingCopy) {
                int id = queueing.getId();
                machine.Sending.remove(queueing); // Safe because we're iterating over a copy
                machine.Sending.add(this.findQueueById(id));
            }

// For machine.requesting
            List<Queueing> requestingCopy = new ArrayList<>(machine.requesting);
            for (Queueing queueing : requestingCopy) {
                int id = queueing.getId();
                machine.requesting.remove(queueing); // Safe because we're iterating over a copy
                machine.requesting.add(this.findQueueById(id));
            }
            machines.add(machine);
        }

        // Restore IDs
        this.machineId = replayTracker.getMachineId();
        this.QueueId = replayTracker.getQueueId();
        System.out.println(this.toString());

        // Restart the simulation
        this.run();
    }

    @Override
    public String toString() {
        return "Simulator{" +
                "monitorThread=" + monitorThread +
                ", machineId=" + machineId +
                ", QueueId=" + QueueId +
                ", queues=" + queues +
                ", machines=" + machines +
                ", threads=" + threads +
                '}';
    }
}
