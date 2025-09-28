package com.example.consolecasino.dto;

// Used to send the row and column for a Mines reveal
public class RevealRequest {
    private int row;
    private int col;

    public RevealRequest(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getters and Setters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}