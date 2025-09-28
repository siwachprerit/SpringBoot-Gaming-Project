package com.example.consolecasino.dto;

import java.util.List;

// Represents the User object received from the userservice
public class User {
    private Long id;
    private String username;
    private double balance;
    private List<GameHistory> gameHistory;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<GameHistory> getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(List<GameHistory> gameHistory) {
        this.gameHistory = gameHistory;
    }
}