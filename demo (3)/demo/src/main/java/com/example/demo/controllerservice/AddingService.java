package com.example.demo.controllerservice;

import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.classes.Simulator;
import org.springframework.stereotype.Service;


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

