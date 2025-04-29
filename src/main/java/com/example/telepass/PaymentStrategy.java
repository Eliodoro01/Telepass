package com.example.telepass;

public interface PaymentStrategy {
    boolean pay(double amount);
}