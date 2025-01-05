package com.example.demo.controllers;

import com.example.demo.classes.Machine;
import com.example.demo.classes.Simulator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SimController {
    Simulator simulator=Simulator.getInstance();
    @PutMapping("/MTQ/{MachineId}/{QueueId}")
    public void MTQ(@PathVariable int MachineId,@PathVariable int QueueId){
          simulator.findMachineById(MachineId).addrequesting(simulator.findQueueById(QueueId));
    }
    @PutMapping("/QTM/{MachineId}/{QueueId}")
    public void QTM(@PathVariable int MachineId,@PathVariable int QueueId){
        simulator.findMachineById(MachineId).addSending(simulator.findQueueById(QueueId));
    }
    @GetMapping("/{capacity}")
    public void start(@PathVariable int capacity){
        simulator.start(capacity);
    }
}
