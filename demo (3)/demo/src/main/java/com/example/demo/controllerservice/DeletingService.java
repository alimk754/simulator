package com.example.demo.controllerservice;

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
    public void deleteMachine(int machineId) {
        sim.deleteMachineById(machineId);
    }
    public void deleteQueue(int queueId){
                sim.deleteQueueById(queueId);
            }
}

