package com.example.telepass;

public class ConcreteVehicleFactory implements VehicleFactory {
    @Override
    public Vehicle createVehicle(String licensePlate, String ownerName, String paymentMethod, String deviceCode) {
        return new Vehicle(licensePlate, ownerName, paymentMethod, deviceCode);
    }
}