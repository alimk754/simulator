package com.example.demo.controllers;


import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.controllerservice.AddingService;
import com.example.demo.dto.MachineDto;
import com.example.demo.dto.QueueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/add")
@CrossOrigin("*")
public class AddingController {
    @Autowired
    private AddingService addingService;
    @PostMapping("")
    public void addMachine(@RequestBody MachineDto machine){
        Machine machine1=new Machine();
        addingService.addMachine(machine1);
        System.out.println("add m");
    }
    @PostMapping("/queue")
    public void addQueue(@RequestBody QueueDto queue)
    {
        Queueing q=new Queueing(queue.capacity);
        addingService.addQueue(q);
        System.out.println("add m");
    }
}
