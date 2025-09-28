package com.example.gameservice.dto;

// This class will be used to send the current row for a Plinko frame
public class PlinkoRequest {
    private int currentRow;
    // Getters and Setters
    public int getCurrentRow() { return currentRow; }
    public void setCurrentRow(int currentRow) { this.currentRow = currentRow; }
}