package com.example.demo.Service;


import com.example.demo.classes.Queueing;
import com.example.demo.classes.Simulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueueService {
    Simulator simulator=Simulator.getInstance();
    public void saveQueue(Queueing q) {
        simulator.addQueue(q);
    }

    public Queueing getQueueById(int id) {
        return simulator.findQueueById(id);
    }
}