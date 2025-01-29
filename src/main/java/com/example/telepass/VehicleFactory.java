package com.example.telepass;

public interface VehicleFactory {
    Vehicle createVehicle(String licensePlate, String ownerName, String paymentMethod, String deviceCode);
}