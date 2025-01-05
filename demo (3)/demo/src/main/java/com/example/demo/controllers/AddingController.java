package com.example.demo.controllers;

import com.example.demo.anotherService.AddingService;
import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.dto.MachineDto;
import com.example.demo.dto.QueueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/add")
public class AddingController {
    @Autowired
    private AddingService addingService;
    @PostMapping("/{simId}")
    public void addMachine(@RequestBody MachineDto machine, @PathVariable int simId){
        Machine machine1=new Machine(machine.color);
        addingService.addMachine(machine1, simId);
    }
    @PostMapping("/queue/{simId}")
    public void addQueue(@RequestBody QueueDto queue, @PathVariable int simId)
    {
        Queueing q=new Queueing(queue.capacity);
        addingService.addQueue(q, simId);
    }
}
