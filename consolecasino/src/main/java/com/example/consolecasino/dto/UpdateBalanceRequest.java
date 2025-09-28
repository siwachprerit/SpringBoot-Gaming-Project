package com.example.consolecasino.dto;

// Used to send balance updates to the userservice
public class UpdateBalanceRequest {
    private double amount;

    public UpdateBalanceRequest(double amount) {
        this.amount = amount;
    }

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}