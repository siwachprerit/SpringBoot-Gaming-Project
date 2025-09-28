package com.example.userservice.controller;

import com.example.userservice.dto.*;
import com.example.userservice.model.GameHistory;
import com.example.userservice.model.User;
import com.example.userservice.repository.GameHistoryRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.PasswordUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final GameHistoryRepository gameHistoryRepository;
    // **NEW**: Inject the ApplicationContext to allow for shutdown
    private final ApplicationContext appContext;

    public UserController(UserRepository userRepository, GameHistoryRepository gameHistoryRepository, ApplicationContext appContext) {
        this.userRepository = userRepository;
        this.gameHistoryRepository = gameHistoryRepository;
        this.appContext = appContext;
    }

    // ... (All your other endpoints like /register, /login, etc., remain here) ...
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        String hashedPassword = PasswordUtils.hashPassword(request.getPassword());
        User newUser = new User(request.getUsername(), hashedPassword, 100.0);
        userRepository.save(newUser);
        UserResponse response = new UserResponse(newUser.getId(), newUser.getUsername(), newUser.getBalance());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        String hashedInput = PasswordUtils.hashPassword(request.getPassword());
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(hashedInput)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        User user = userOptional.get();
        UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getBalance());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(user.getBalance()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{username}/balance/update")
    public ResponseEntity<?> updateBalance(@PathVariable String username, @RequestBody UpdateBalanceRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOptional.get();
        double newBalance = user.getBalance() + request.getAmount();
        user.setBalance(newBalance < 0 ? 0 : newBalance);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{username}/history")
    public ResponseEntity<?> addGameHistory(@PathVariable String username, @RequestBody GameHistoryRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        GameHistory history = new GameHistory(request.getGameName(), request.getBetAmount(), request.getNetAmount(), userOptional.get());
        gameHistoryRepository.save(history);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{username}/history")
    public ResponseEntity<List<GameHistory>> getGameHistory(@PathVariable String username) {
         Optional<User> userOptional = userRepository.findByUsername(username);
         if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(userOptional.get().getGameHistory());
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<UserResponse>> getLeaderboard() {
        List<User> topUsers = userRepository.findTop10ByOrderByBalanceDesc();
        List<UserResponse> response = topUsers.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getBalance()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }


    // **NEW**: Add a shutdown endpoint for development convenience
    @PostMapping("/shutdown")
    public void shutdown() {
        SpringApplication.exit(appContext, () -> 0);
    }
}