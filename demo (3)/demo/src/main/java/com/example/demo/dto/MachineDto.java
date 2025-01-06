package com.example.demo.dto;

public class MachineDto {
    private int machineId;
    private String color;
    private boolean isWorking;

    // Constructor, getters, and setters
    public MachineDto(int machineId, String color, boolean isWorking) {
        this.machineId = machineId;
        this.color = color;
        this.isWorking = isWorking;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }
}
