package com.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gameName; // e.g., "Mines" or "Plinko"
    private double betAmount;
    private double netAmount; // The amount won or lost (e.g., +50 or -10)
    private LocalDateTime timestamp;

    // Many game history records belong to one user.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // This creates a 'user_id' foreign key column
    @JsonIgnore // This is important! It prevents infinite loops when we create our API.
    private User user;

    // Constructors, getters, and setters...
    public GameHistory() {}

    public GameHistory(String gameName, double betAmount, double netAmount, User user) {
        this.gameName = gameName;
        this.betAmount = betAmount;
        this.netAmount = netAmount;
        this.user = user;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters for all fields...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }
    public double getBetAmount() { return betAmount; }
    public void setBetAmount(double betAmount) { this.betAmount = betAmount; }
    public double getNetAmount() { return netAmount; }
    public void setNetAmount(double netAmount) { this.netAmount = netAmount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}