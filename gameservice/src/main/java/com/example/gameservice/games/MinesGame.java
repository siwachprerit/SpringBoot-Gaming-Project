package com.example.gameservice.games;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component // Marks this as a Spring bean so we can use it in our controller
public class MinesGame {

    private final int SIZE = 5;
    private final int MINES = 5;
    private boolean[][] mines;
    private String[][] display;
    private double currentMultiplier;

    // This method starts a new game
    public String[][] start() {
        this.mines = new boolean[SIZE][SIZE];
        this.display = new String[SIZE][SIZE];
        this.currentMultiplier = 1.0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                display[r][c] = "■";
            }
        }
        placeMines();
        return display;
    }

    // This method handles a user's move
    public RevealResult reveal(int r, int c) {
        if (mines[r][c]) {
            display[r][c] = "💣";
            return new RevealResult(display, 0, true, "Boom! You hit a mine.");
        } else {
            display[r][c] = "💎";
            currentMultiplier += 0.5;
            return new RevealResult(display, currentMultiplier, false, "Safe! Multiplier is now x" + String.format("%.2f", currentMultiplier));
        }
    }

    private void placeMines() {
        Random r = new Random();
        int placed = 0;
        while (placed < MINES) {
            int a = r.nextInt(SIZE);
            int b = r.nextInt(SIZE);
            if (!mines[a][b]) {
                mines[a][b] = true;
                placed++;
            }
        }
    }
}