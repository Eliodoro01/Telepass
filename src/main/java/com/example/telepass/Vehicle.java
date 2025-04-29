package com.example.telepass;

public class Vehicle implements IVehicle {
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

    @Override
    public double enterTollBooth() {
        return 2.50; // Tariffa base
    }

    @Override
    public double exitTollBooth() {
        return 2.50; // Tariffa base
    }

    // Getters
    @Override
    public String getLicensePlate() { return licensePlate; }
    @Override
    public String getOwnerName() { return ownerName; }
    @Override
    public String getPaymentMethod() { return paymentMethod; }
    @Override
    public String getDeviceCode() { return deviceCode; }
}


