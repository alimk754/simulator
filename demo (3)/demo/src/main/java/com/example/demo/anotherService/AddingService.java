package com.example.demo.anotherService;

import com.example.demo.Service.MachineService;
import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.classes.Simulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddingService {
    Simulator sim=Simulator.getInstance();

    public void addMachine(Machine machine){
            sim.addMachine(machine);
    }
    public void addQueue(Queueing queue){
            sim.addQueue(queue);
    }
}
