package com.example.demo.controllers;

import com.example.demo.anotherService.AddingService;
import com.example.demo.anotherService.DeletingService;
import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete")
public class DeletingController {
    @Autowired private DeletingService deletingService;
    @PostMapping("/{MachineId}/{simId}")
    public void addMachine(@PathVariable int MachineId, @PathVariable int simId){
        deletingService.deleteMachine(MachineId, simId);
    }
    @PostMapping("/{queueId}/{simId}")
    public void addQueue(@PathVariable int queueId, @PathVariable int simId){
        deletingService.deleteQueue(queueId, simId);
    }
}
