package com.example.gameservice.controller;

import com.example.gameservice.dto.PlinkoRequest;
import com.example.gameservice.dto.RevealRequest;
import com.example.gameservice.games.MinesGame;
import com.example.gameservice.games.PlinkoGame;
import com.example.gameservice.games.RevealResult;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/games")
public class GameController {

    private final MinesGame minesGame;
    private final PlinkoGame plinkoGame;
    // **NEW**: Inject the ApplicationContext to allow for shutdown
    private final ApplicationContext appContext;

    public GameController(MinesGame minesGame, PlinkoGame plinkoGame, ApplicationContext appContext) {
        this.minesGame = minesGame;
        this.plinkoGame = plinkoGame;
        this.appContext = appContext;
    }

    // ... (All your other game endpoints like /mines/start, etc., remain here) ...
    @PostMapping("/mines/start")
    public ResponseEntity<String[][]> startMines() {
        return ResponseEntity.ok(minesGame.start());
    }

    @PostMapping("/mines/reveal")
    public ResponseEntity<RevealResult> revealMine(@RequestBody RevealRequest request) {
        return ResponseEntity.ok(minesGame.reveal(request.getRow(), request.getCol()));
    }

    @PostMapping("/plinko/start")
    public ResponseEntity<Void> startPlinko() {
        plinkoGame.start();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/plinko/nextframe")
    public ResponseEntity<String> getPlinkoNextFrame(@RequestBody PlinkoRequest request) {
        return ResponseEntity.ok(plinkoGame.getNextFrame(request.getCurrentRow()));
    }

    @GetMapping("/plinko/multiplier")
    public ResponseEntity<Map<String, Double>> getPlinkoMultiplier() {
        return ResponseEntity.ok(Map.of("multiplier", plinkoGame.getMultiplier()));
    }


    // **NEW**: Add a shutdown endpoint for development convenience
    @PostMapping("/shutdown")
    public void shutdown() {
        SpringApplication.exit(appContext, () -> 0);
    }
}