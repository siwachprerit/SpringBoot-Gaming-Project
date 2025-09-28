package com.example.consolecasino.dto;

// Used to send new game history to the userservice
public class GameHistoryRequest {
    private String gameName;
    private double betAmount;
    private double netAmount;

    public GameHistoryRequest(String gameName, double betAmount, double netAmount) {
        this.gameName = gameName;
        this.betAmount = betAmount;
        this.netAmount = netAmount;
    }

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
}