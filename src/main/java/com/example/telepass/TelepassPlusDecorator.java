package com.example.telepass;

public class TelepassPlusDecorator implements IVehicle {
    private IVehicle decoratedVehicle;

    public TelepassPlusDecorator(IVehicle vehicle) {
        this.decoratedVehicle = vehicle;
    }

    @Override
    public double enterTollBooth() {
        return decoratedVehicle.enterTollBooth() * 0.8; // Sconto 20%
    }

    @Override
    public double exitTollBooth() {
        return decoratedVehicle.exitTollBooth() * 0.8; // Sconto 20%
    }

    // Delegazione dei getters
    @Override
    public String getLicensePlate() { return decoratedVehicle.getLicensePlate(); }
    @Override
    public String getOwnerName() { return decoratedVehicle.getOwnerName(); }
    @Override
    public String getPaymentMethod() { return decoratedVehicle.getPaymentMethod(); }
    @Override
    public String getDeviceCode() { return decoratedVehicle.getDeviceCode(); }
}