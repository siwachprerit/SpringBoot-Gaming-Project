package com.example.consolecasino.dto;

import java.time.LocalDateTime;

// Represents a GameHistory object received from the userservice
public class GameHistory {
    private String gameName;
    private double betAmount;
    private double netAmount;
    private LocalDateTime timestamp;

    // Getters and Setters
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}