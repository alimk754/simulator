package com.example.demo.Service;

import com.example.demo.Repos.Queue_repo;

import com.example.demo.classes.Queueing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueueService {
    @Autowired
    private Queue_repo QueueRepo;
    public Queueing saveQueue(Queueing product) {
        return QueueRepo.save(product);
    }

    public Optional<Queueing> getQueueById(int id) {
        return QueueRepo.findById(id);
    }

    public List<Queueing> getAllQueues() {
        return QueueRepo.findAll();
    }

    public void deleteQueue(int id) {
        QueueRepo.deleteById(id);
    }
}