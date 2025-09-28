package com.example.consolecasino;

import com.example.consolecasino.console.ConsoleRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConsolecasinoApplication implements CommandLineRunner {

    private final ConsoleRunner consoleRunner;

    // Spring will automatically inject the ConsoleRunner we created
    public ConsolecasinoApplication(ConsoleRunner consoleRunner) {
        this.consoleRunner = consoleRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsolecasinoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // This is the entry point for our console application logic
        consoleRunner.run();
    }
}