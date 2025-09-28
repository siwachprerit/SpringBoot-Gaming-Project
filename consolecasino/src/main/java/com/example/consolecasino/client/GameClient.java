package com.example.consolecasino.client;

import com.example.consolecasino.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// This client sends requests to http://localhost:8082
@FeignClient(name = "gameservice", url = "http://localhost:8082")
public interface GameClient {

    @PostMapping("/games/mines/start")
    String[][] startMines();

    @PostMapping("/games/mines/reveal")
    RevealResult revealMine(@RequestBody RevealRequest request);

    @PostMapping("/games/plinko/start")
    void startPlinko();

    @PostMapping("/games/plinko/nextframe")
    String getPlinkoNextFrame(@RequestBody PlinkoRequest request);

    @GetMapping("/games/plinko/multiplier")
    Map<String, Double> getPlinkoMultiplier();
    
    @PostMapping("/games/shutdown")
    void shutdown();
}