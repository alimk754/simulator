package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulator {
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
    //simulation
    public synchronized void start(int size){
        Queueing mainQueue=queues.get(0);
        for(int i=0;i<size;i++){
            Products p=new Products();
            mainQueue.queue.add(p);
        }
        System.out.println(mainQueue.queue.size());
        System.out.println(machines.size());
        System.out.println(queues.size());
        for (Machine machine :machines){
            Thread thread =new Thread(machine);
            threads.add(thread);
            thread.start();
        }
        new Thread(this::monitorThreads).start();
    }
    // Method to monitor threads
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

    // Method to interrupt all threads
    private void interruptAllThreads() {
        for (Thread thread : threads) {
            thread.interrupt(); // Interrupt each thread
        }
    }

    public void delete(){
        for (Thread thread :threads){
            thread.interrupt();
        }
        threads.clear();
        machines.clear();
        queues.clear();
        machineId=0;
        QueueId=0;
    }
}
