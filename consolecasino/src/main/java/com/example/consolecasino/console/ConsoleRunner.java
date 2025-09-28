package com.example.consolecasino.console;

import com.example.consolecasino.client.GameClient;
import com.example.consolecasino.client.UserClient;
import com.example.consolecasino.dto.*;
import feign.FeignException;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

@Component
public class ConsoleRunner {

    private final UserClient userClient;
    private final GameClient gameClient;
    private final CircuitBreakerFactory<Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration, Resilience4JConfigBuilder> cbFactory;
    private final Scanner scanner = new Scanner(System.in);
    private User currentUser;

    public ConsoleRunner(UserClient userClient, GameClient gameClient, CircuitBreakerFactory<Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration, Resilience4JConfigBuilder> cbFactory) {
        this.userClient = userClient;
        this.gameClient = gameClient;
        this.cbFactory = cbFactory;
    }

    public void run() {
        while (true) {
            showWelcomeMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": handleLogin(); break;
                case "2": handleSignup(); break;
                case "3":
                    System.out.println("Thanks for visiting Console Casino! Shutting down all services...");
                    // **NEW**: Call the shutdown endpoints on the other services
                    try {
                        userClient.shutdown();
                        gameClient.shutdown();
                    } catch (Exception e) {
                        // Ignore exceptions during shutdown
                    }
                    System.out.println("Goodbye.");
                    return; // This will terminate the consolecasino application
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showWelcomeMenu() {
        System.out.println("\n=== Welcome to Console Casino ===");
        System.out.println("1. Log In");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private void handleLogin() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            UserResponse response = userClient.login(new LoginRequest(username, password));
            this.currentUser = new User();
            this.currentUser.setId(response.getId());
            this.currentUser.setUsername(response.getUsername());
            this.currentUser.setBalance(response.getBalance());

            System.out.println("\nWelcome back, " + currentUser.getUsername() + "!");
            showCasinoMenu();
        } catch (FeignException e) {
            if (e.status() == 401) {
                System.out.println("Error: Login failed. Please check your username and password.");
            } else {
                System.out.println("Error: Could not connect to the user service. Please ensure it is running.");
            }
        }
    }

    private void handleSignup() {
        try {
            System.out.print("Choose a username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Create a password: ");
            String password = scanner.nextLine().trim();

            UserResponse response = userClient.register(new RegisterRequest(username, password));
            this.currentUser = new User();
            this.currentUser.setId(response.getId());
            this.currentUser.setUsername(response.getUsername());
            this.currentUser.setBalance(response.getBalance());

            System.out.println("\nAccount created successfully! Your starting balance is 100.00.");
            showCasinoMenu();
        } catch (FeignException e) {
            if (e.status() == 409) {
                System.out.println("Error: That username is already taken. Please try another.");
            } else {
                System.out.println("Error: Could not connect to the user service. Please ensure it is running.");
            }
        }
    }

    private void showCasinoMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Casino Menu ===");
            System.out.printf("Your Balance: %.2f%n", currentUser.getBalance());
            System.out.println("1. Play Mines Game");
            System.out.println("2. Play Plinko Game");
            System.out.println("3. View My Game History");
            System.out.println("4. View Leaderboard");
            System.out.println("5. Log Out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": playMines(); break;
                case "2": playPlinko(); break;
                case "3": viewGameHistory(); break;
                case "4": viewLeaderboard(); break;
                case "5":
                    System.out.println("Logging out...");
                    this.currentUser = null;
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private double getBetAmount() {
        while (true) {
            System.out.print("Enter bet amount: ");
            try {
                double bet = Double.parseDouble(scanner.nextLine().trim());
                if (bet <= 0) {
                    System.out.println("Bet must be a positive number.");
                } else if (bet > currentUser.getBalance()) {
                    System.out.println("Insufficient balance.");
                    return -1;
                } else {
                    return bet;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please try again.");
            }
        }
    }
    
    private void playMines() {
        double bet = getBetAmount();
        if (bet < 0) return;

        CircuitBreaker gameApiCircuitBreaker = cbFactory.create("gameApi");
        
        String[][] display = gameApiCircuitBreaker.run(
            () -> gameClient.startMines(), 
            throwable -> {
                System.out.println("Error: The Mines game service is currently unavailable. Your bet has been refunded.");
                return null;
            }
        );

        if (display == null) return;
        double currentMultiplier = 1.0;

        while (true) {
            printMinesGrid(display);
            System.out.println("Current Multiplier: x" + String.format("%.2f", currentMultiplier));
            System.out.print("Choose row (0-4), or type 'cashout': ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("cashout")) {
                double winnings = bet * currentMultiplier;
                double net = winnings - bet;
                System.out.printf("You cashed out! Winnings: %.2f (Net: +%.2f)%n", winnings, net);
                updateBalanceAndHistory("Mines", bet, net);
                break;
            }

            try {
                int r = Integer.parseInt(input);
                System.out.print("Choose col (0-4): ");
                int c = Integer.parseInt(scanner.nextLine().trim());

                RevealResult result = gameApiCircuitBreaker.run(() -> gameClient.revealMine(new RevealRequest(r, c)), t -> null);
                if(result == null) {
                    System.out.println("Error: Game service is unavailable. Refunding bet.");
                    return;
                }

                display = result.getDisplay();
                System.out.println(result.getMessage());

                if (result.isGameOver()) {
                    printMinesGrid(display);
                    System.out.println("You lose your bet of " + String.format("%.2f", bet));
                    updateBalanceAndHistory("Mines", bet, -bet);
                    break;
                } else {
                    currentMultiplier = result.getMultiplier();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numbers between 0 and 4.");
            }
        }
    }
    
    private void playPlinko() {
        double bet = getBetAmount();
        if (bet < 0) return;

        CircuitBreaker gameApiCircuitBreaker = cbFactory.create("gameApi");
        
        gameApiCircuitBreaker.run(() -> {
            gameClient.startPlinko();
            return null;
        }, throwable -> {
            System.out.println("Error: Plinko service is down. Refunding bet.");
            return null;
        });

        System.out.print("Press Enter to drop the ball...");
        scanner.nextLine();

        for (int r = 0; r < 10; r++) {
            final int currentRow = r;
            String frame = gameApiCircuitBreaker.run(() -> gameClient.getPlinkoNextFrame(new PlinkoRequest(currentRow)), t -> "Service Down");
            
            if (frame.equals("Service Down")) {
                System.out.println("Error: Plinko service is unavailable during play. Refunding bet.");
                return;
            }

            clearConsoleAndPrint(frame);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        
        Map<String, Double> multiplierResult = gameApiCircuitBreaker.run(() -> gameClient.getPlinkoMultiplier(), t -> Map.of("multiplier", 0.0));
        double multiplier = multiplierResult.get("multiplier");

        if (multiplier == 0.0) {
             System.out.println("Error: Could not get final result. Refunding bet.");
             return;
        }

        double winnings = bet * multiplier;
        double net = winnings - bet;
        System.out.printf("\nBall landed! Multiplier: x%.2f%n", multiplier);
        System.out.printf("You won: %.2f (Net: %.2f)%n", winnings, net);
        updateBalanceAndHistory("Plinko", bet, net);
    }

    private void updateBalanceAndHistory(String gameName, double bet, double netAmount) {
        try {
            userClient.updateBalance(currentUser.getUsername(), new UpdateBalanceRequest(netAmount));
            userClient.addGameHistory(currentUser.getUsername(), new GameHistoryRequest(gameName, bet, netAmount));
            currentUser.setBalance(currentUser.getBalance() + netAmount);
            System.out.printf("Your new balance is: %.2f%n", currentUser.getBalance());
        } catch (FeignException e) {
            System.out.println("Critical Error: Could not update your balance. Please contact support.");
        }
    }
    
    private void viewGameHistory() {
        try {
            var history = userClient.getGameHistory(currentUser.getUsername());
            System.out.println("\n--- Your Game History ---");
            if (history.isEmpty()) {
                System.out.println("You haven't played any games yet.");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                for (GameHistory item : history) {
                    // **FIX**: Corrected the format string from "%-+.2f" to "%+8.2f"
                    // This ensures the sign is always shown and pads the number to a width of 8 characters.
                    System.out.printf("Time: %s | Game: %-8s | Bet: %-7.2f | Net: %+8.2f%n",
                            item.getTimestamp().format(formatter),
                            item.getGameName(),
                            item.getBetAmount(),
                            item.getNetAmount());
                }
            }
        } catch (FeignException e) {
            System.out.println("Error: Could not retrieve your game history.");
        }
    }

    private void viewLeaderboard() {
        try {
            var leaderboard = userClient.getLeaderboard();
            System.out.println("\n--- Top 10 Players ---");
            int rank = 1;
            for (UserResponse user : leaderboard) {
                System.out.printf("%d. %-15s | Balance: %.2f%n", rank++, user.getUsername(), user.getBalance());
            }
        } catch (FeignException e) {
            System.out.println("Error: Could not retrieve the leaderboard.");
        }
    }
    
    private void printMinesGrid(String[][] display) {
        System.out.println();
        for (String[] row : display) {
            for (String cell : row) {
                System.out.print(cell + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void clearConsoleAndPrint(String text) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(text);
    }
}