package com.example.demo.anotherService;

import com.example.demo.Service.MachineService;
import com.example.demo.Service.QueueService;
import com.example.demo.Service.SimService;
import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.classes.Simulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeletingService {
    @Autowired private SimService simService;
    @Autowired MachineService machineService;
    @Autowired QueueService queueService;
    public void deleteMachine(Machine machine, int sim_id){
        Optional<Simulator> simOP=simService.getSimById(sim_id);
        if(simOP.isPresent()){
            Simulator sim=simOP.get();
            sim.deleteMachine(machine);
            machineService.getMachineById(machine.getId());
            simService.saveSim(sim);
        }
    }
    public void deleteQueue(Queueing queue, int sim_id){
        Optional<Simulator> simOP=simService.getSimById(sim_id);
        if(simOP.isPresent()){
            Simulator sim=simOP.get();
            sim.deleteQueue(queue);
            machineService.getMachineById(queue.getId());
            simService.saveSim(sim);
        }
    }
}
