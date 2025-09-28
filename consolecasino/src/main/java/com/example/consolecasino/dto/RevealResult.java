package com.example.consolecasino.dto;

// Represents the result of a Mines reveal from the gameservice
public class RevealResult {
    private String[][] display;
    private double multiplier;
    private boolean gameOver;
    private String message;

    // **FIX**: Added the required no-argument constructor for JSON deserialization
    public RevealResult() {}

    // Getters and Setters
    public String[][] getDisplay() {
        return display;
    }

    public void setDisplay(String[][] display) {
        this.display = display;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}