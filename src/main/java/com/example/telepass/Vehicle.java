package com.example.telepass;

public class Vehicle {
    private String licensePlate;
    private String ownerName;
    private String paymentMethod;
    private String deviceCode;

    public Vehicle(String licensePlate, String ownerName, String paymentMethod, String deviceCode) {
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.paymentMethod = paymentMethod;
        this.deviceCode = deviceCode;
    }

    public double enterTollBooth() {
        return 2.50; // Tariffa fissa
    }

    public double exitTollBooth() {
        return 2.50; // Tariffa fissa
    }
}