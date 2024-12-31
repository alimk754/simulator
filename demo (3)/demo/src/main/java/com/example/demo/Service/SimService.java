package com.example.demo.Service;

import com.example.demo.Repos.Product_repo;
import com.example.demo.Repos.Sim_repo;
import com.example.demo.classes.Simulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimService {
    @Autowired
    private Sim_repo simRepo;
    public Simulator saveSim(Simulator product) {
        return simRepo.save(product);
    }

    public Optional<Simulator> getSimById(int id) {
        return simRepo.findById(id);
    }

    public List<Simulator> getAllSims() {
        return simRepo.findAll();
    }

    public void deleteSim(int id) {
        simRepo.deleteById(id);
    }
}