package com.example.consolecasino.client;

import com.example.consolecasino.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "userservice", url = "http://localhost:8081")
public interface UserClient {

    @PostMapping("/users/register")
    UserResponse register(@RequestBody RegisterRequest request);

    @PostMapping("/users/login")
    UserResponse login(@RequestBody LoginRequest request);

    @PostMapping("/users/{username}/balance/update")
    void updateBalance(@PathVariable("username") String username, @RequestBody UpdateBalanceRequest request);

    @GetMapping("/users/{username}/history")
    List<GameHistory> getGameHistory(@PathVariable("username") String username);

    @PostMapping("/users/{username}/history")
    void addGameHistory(@PathVariable("username") String username, @RequestBody GameHistoryRequest request);

    @GetMapping("/users/leaderboard")
    List<UserResponse> getLeaderboard();
    
    @PostMapping("/users/shutdown")
    void shutdown();
}