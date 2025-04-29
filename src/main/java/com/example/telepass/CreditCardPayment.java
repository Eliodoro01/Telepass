package com.example.telepass;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Pagamento di " + amount + "€ con carta di credito.");
        return true; // Simula pagamento riuscito
    }
}