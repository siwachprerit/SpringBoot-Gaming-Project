package com.example.consolecasino.dto;

// Used to send the current row for a Plinko frame
public class PlinkoRequest {
    private int currentRow;

    public PlinkoRequest(int currentRow) {
        this.currentRow = currentRow;
    }

    // Getters and Setters
    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
}