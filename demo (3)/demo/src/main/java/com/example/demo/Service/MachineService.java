package com.example.demo.Service;


import com.example.demo.classes.Machine;
import com.example.demo.classes.Simulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    Simulator simulator =Simulator.getInstance();
    public void saveMachine(Machine machine) {
        simulator.addMachine(machine);
    }

    public Machine getMachineById(int id) {
        return simulator.findMachineById(id);
    }
    public void deleteMachine(int id) {
        simulator.deleteMachineById(id);
    }
}