package com.example.userservice.dto;

// This object is a safe, clean representation of a User for our API
public class UserResponse {
    private Long id;
    private String username;
    private double balance;

    public UserResponse(Long id, String username, double balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}