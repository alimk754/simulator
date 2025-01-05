package com.example.demo.anotherService;

import com.example.demo.Service.MachineService;
import com.example.demo.Service.QueueService;

import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.classes.Simulator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeletingService {
    Simulator sim =Simulator.getInstance();
    public void deleteMachine(int machineId, int sim_id) {
        sim.deleteMachineById(machineId);
    }
    public void deleteQueue(int queueId, int sim_id){
                sim.deleteQueueById(queueId);
            }
}

