package com.example.demo.controllers;

import com.example.demo.anotherService.AddingService;
import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/add")
public class AddingController {
    @Autowired
    private AddingService addingService;
    @PostMapping("/{sim_id}")
    public void addMachine(@RequestBody Machine machine, @PathVariable int sim_id){
        addingService.addMachine(machine, sim_id);
    }
    @PostMapping("/queue/{simId}")
    public void addQueue(@RequestBody Queueing queue, @PathVariable int simId){
        addingService.addQueue(queue, simId);
    }
}
