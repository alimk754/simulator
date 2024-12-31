package com.example.demo.Service;

import com.example.demo.Repos.Machine_repo;
import com.example.demo.Repos.Product_repo;
import com.example.demo.classes.Machine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {
    @Autowired
    private Machine_repo machineRepo;
    public Machine saveMachine(Machine product) {
        return machineRepo.save(product);
    }

    public Optional<Machine> getMachineById(int id) {
        return machineRepo.findById(id);
    }

    public List<Machine> getAllMachines() {
        return machineRepo.findAll();
    }

    public void deleteMachine(int id) {
        machineRepo.deleteById(id);
    }
}