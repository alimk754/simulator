package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Sim")
public class Simulator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sim_id")
    private int id;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Queueing main;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "sim_queue")
    private List<Queueing> queues;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "sim_machines")
    private List<Machine> machines;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Queueing getMain() {
        return main;
    }

    public void setMain(Queueing main) {
        this.main = main;
    }

    public List<Queueing> getQueues() {
        return queues;
    }

    public void setQueues(List<Queueing> queues) {
        this.queues = queues;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }
    public void addMachine(Machine m){
        machines.add(m);
    }
    public void deleteMachine(Machine m){
        machines.remove(m);
    }
    public void addQueue(Queueing q){
        queues.add(q);
    }
    public void deleteQueue(Queueing q){
        queues.remove(q);
    }
}
