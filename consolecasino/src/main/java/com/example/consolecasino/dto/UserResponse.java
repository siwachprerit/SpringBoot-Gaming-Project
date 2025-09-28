package com.example.consolecasino.dto;

public class UserResponse {
    private Long id;
    private String username;
    private double balance;
    
    // An empty constructor is needed for the JSON deserialization
    public UserResponse() {}

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