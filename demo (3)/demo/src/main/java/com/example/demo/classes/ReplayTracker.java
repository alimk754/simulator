package com.example.demo.classes;

import com.example.demo.mementos.MachineSnapShot;
import com.example.demo.mementos.QueuesSnapShot;

import java.util.ArrayList;
import java.util.List;

public class ReplayTracker {
    private static ReplayTracker instance;
    private List<MachineSnapShot> machineSnapShots = new ArrayList<>();
    private List<QueuesSnapShot> queuesSnapShots = new ArrayList<>();
    private int machineId;
    private int queueId;

    // Private constructor to enforce singleton pattern
    private ReplayTracker() {}

    // Singleton instance getter
    public static ReplayTracker getInstance() {
        if (instance == null) {
            instance = new ReplayTracker();
        }
        return instance;
    }

    // Take a snapshot of the current state
    public static void takeSnapshot(List<Machine> machines, List<Queueing> queueingList, int machineId, int queueId) {
        ReplayTracker tracker = getInstance();
        tracker.queuesSnapShots.clear();
        tracker.machineSnapShots.clear();
        for (Machine machine : machines) {
            tracker.machineSnapShots.add(new MachineSnapShot(machine));
        }
        for (Queueing queueing : queueingList) {
            tracker.queuesSnapShots.add(new QueuesSnapShot(queueing));
        }
        tracker.machineId = machineId;
        tracker.queueId = queueId;
    }

    // Getters and setters
    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public List<MachineSnapShot> getMachineSnapShots() {
        return machineSnapShots;
    }

    public void setMachineSnapShots(List<MachineSnapShot> machineSnapShots) {
        this.machineSnapShots = machineSnapShots;
    }

    public List<QueuesSnapShot> getQueuesSnapShots() {
        return queuesSnapShots;
    }

    public void setQueuesSnapShots(List<QueuesSnapShot> queuesSnapShots) {
        this.queuesSnapShots = queuesSnapShots;
    }
}