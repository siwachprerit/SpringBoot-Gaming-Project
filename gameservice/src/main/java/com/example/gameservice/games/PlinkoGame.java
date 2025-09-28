package com.example.gameservice.games;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class PlinkoGame {

    private final int ROWS = 10;
    private final Random random = new Random();
    private int ballPosition;

    public void start() {
        // Start the ball in the middle of the top row (which only has one slot, index 0)
        this.ballPosition = 0;
    }

    // This method calculates and returns a single frame of the animation
    public String getNextFrame(int currentRow) {
        // First, create the visual for the ball at its current position for the given row.
        String currentFrame = drawPyramid(currentRow, this.ballPosition);

        // **FIX**: New logic to determine the ball's position for the *next* row.
        // In Plinko, when a ball at position 'p' falls, it can land at 'p' (left) or 'p+1' (right) in the row below.
        // We use a simple 50/50 random choice to simulate this.
        if (currentRow < ROWS - 1) {
            if (random.nextBoolean()) {
                // Go Right: The position index for the next row increases by 1.
                this.ballPosition++;
            }
            // Go Left: The position index stays the same. No code needed.
        }

        return currentFrame;
    }

    public double getMultiplier() {
        // The final multiplier is based on how far from the center the ball lands.
        int middle = (ROWS - 1) / 2;
        int distance = Math.abs(ballPosition - middle);
        return 1 + distance * 2.0;
    }

    // This now returns a String instead of printing to console
    private String drawPyramid(int currentRow, int ballPos) {
        StringBuilder finalPyramid = new StringBuilder();
        for (int r = 0; r < ROWS; r++) {
            StringBuilder sb = new StringBuilder();
            int spaces = ROWS - r;
            for (int s = 0; s < spaces; s++) sb.append(" ");
            for (int c = 0; c <= r; c++) {
                if (r == currentRow && c == ballPos) sb.append("○ ");
                else sb.append("● ");
            }
            finalPyramid.append(sb.toString()).append("\n");
        }
        return finalPyramid.toString();
    }
}