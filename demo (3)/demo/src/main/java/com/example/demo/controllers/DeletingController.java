package com.example.demo.controllers;

import com.example.demo.anotherService.AddingService;
import com.example.demo.anotherService.DeletingService;
import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete")
@CrossOrigin("*")
public class DeletingController {
    @Autowired private DeletingService deletingService;
    @PostMapping("/{MachineId}")
    public void addMachine(@PathVariable int MachineId){
        deletingService.deleteMachine(MachineId);
    }
    @PostMapping("/{queueId}")
    public void addQueue(@PathVariable int queueId){
        deletingService.deleteQueue(queueId);
    }
}
