package com.example.demo.subscribers;

import com.example.demo.classes.Machine;

public interface MachineSubscriber {
    public void updateMachineState(Machine machine);
}
