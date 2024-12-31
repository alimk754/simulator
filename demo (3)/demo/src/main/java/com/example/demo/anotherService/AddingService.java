package com.example.demo.anotherService;

import com.example.demo.Service.MachineService;
import com.example.demo.Service.SimService;
import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.classes.Simulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddingService {
    @Autowired private SimService simService;

    public void addMachine(Machine machine,int sim_id){
        Optional<Simulator> simOP=simService.getSimById(sim_id);
        if(simOP.isPresent()){
            Simulator sim=simOP.get();
            sim.addMachine(machine);
            simService.saveSim(sim);
        }
    }
    public void addQueue(Queueing queue, int sim_id){
        Optional<Simulator> simOP=simService.getSimById(sim_id);
        if(simOP.isPresent()){
            Simulator sim=simOP.get();
            sim.addQueue(queue);
            simService.saveSim(sim);
        }
    }
}
