package com.example.demo.mementos;

import com.example.demo.classes.Machine;
import com.example.demo.classes.Queueing;
import com.example.demo.subscribers.MachineSubscriber;

import java.util.ArrayList;
import java.util.List;

public class MachineSnapShot {
    private int MachineId;
    private String color;
    private final String ogcolor="#000000";
    int seconds;
    List<Queueing> Sending=new ArrayList<>();
    List<Queueing> requesting=new ArrayList<>();
    boolean isWorking = false;

    public int getMachineId() {
        return MachineId;
    }

    public void setMachineId(int machineId) {
        MachineId = machineId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOgcolor() {
        return ogcolor;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public List<Queueing> getSending() {
        return Sending;
    }

    public void setSending(List<Queueing> sending) {
        Sending = sending;
    }

    public List<Queueing> getRequesting() {
        return requesting;
    }

    public void setRequesting(List<Queueing> requesting) {
        this.requesting = requesting;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
    MachineSnapShot(Machine m){
        this.color=m.getColor();
        this.isWorking= m.isWorking();
        this.seconds=m.getSeconds();
        this.MachineId=m.getMachineId();
        this.requesting=m.getRequesting();
        this.Sending=m.getSending();
    }
}
