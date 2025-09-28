package com.example.userservice.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // This explicitly names our table "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private double balance;

    // A user can have many game history records.
    // 'cascade = CascadeType.ALL' means if a user is deleted, their history is also deleted.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GameHistory> gameHistory = new ArrayList<>();

    // Constructors, getters, and setters
    public User() {}

    public User(String username, String password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    // Getters and Setters for all fields...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public List<GameHistory> getGameHistory() { return gameHistory; }
    public void setGameHistory(List<GameHistory> gameHistory) { this.gameHistory = gameHistory; }
}