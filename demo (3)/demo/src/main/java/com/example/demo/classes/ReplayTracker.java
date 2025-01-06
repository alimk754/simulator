package com.example.demo.classes;

import com.example.demo.mementos.MachineSnapShot;
import com.example.demo.mementos.QueuesSnapShot;

import java.util.List;

public class ReplayTracker {
      private ReplayTracker instance;
      private List<MachineSnapShot> machineSnapShots;
      private List<QueuesSnapShot> queuesSnapShots;

    ReplayTracker(){
    }
    public ReplayTracker getInstance() {
        if(instance!=null) instance =new ReplayTracker();
        return instance;
    }

    public void setInstance(ReplayTracker instance) {
        this.instance = instance;
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
