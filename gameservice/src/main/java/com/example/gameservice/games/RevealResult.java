package com.example.gameservice.games;

public class RevealResult {
    private String[][] display;
    private double multiplier;
    private boolean gameOver;
    private String message;

    public RevealResult(String[][] display, double multiplier, boolean gameOver, String message) {
        this.display = display;
        this.multiplier = multiplier;
        this.gameOver = gameOver;
        this.message = message;
    }
    // Getters and Setters for all fields...
    public String[][] getDisplay() { return display; }
    public void setDisplay(String[][] display) { this.display = display; }
    public double getMultiplier() { return multiplier; }
    public void setMultiplier(double multiplier) { this.multiplier = multiplier; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}