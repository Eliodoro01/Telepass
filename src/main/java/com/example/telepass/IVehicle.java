package com.example.telepass;

public interface IVehicle {
    double enterTollBooth();
    double exitTollBooth();
    String getLicensePlate();
    String getOwnerName();
    String getPaymentMethod();
    String getDeviceCode();
}