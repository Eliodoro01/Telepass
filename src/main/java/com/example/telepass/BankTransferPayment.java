package com.example.telepass;

public class BankTransferPayment implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Pagamento di " + amount + "€ con bonifico.");
        return true;
    }
}