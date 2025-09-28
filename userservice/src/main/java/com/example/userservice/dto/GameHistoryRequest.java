package com.example.userservice.dto;

public class GameHistoryRequest {
    private String gameName;
    private double betAmount;
    private double netAmount;
    // Getters and Setters
    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }
    public double getBetAmount() { return betAmount; }
    public void setBetAmount(double betAmount) { this.betAmount = betAmount; }
    public double getNetAmount() { return netAmount; }
    public void setNetAmount(double netAmount) { this.netAmount = netAmount; }
}