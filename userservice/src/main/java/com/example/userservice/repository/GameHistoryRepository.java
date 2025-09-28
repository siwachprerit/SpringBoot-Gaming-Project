package com.example.userservice.repository;

import com.example.userservice.model.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    // We don't need any custom methods here for now.
    // JpaRepository already gives us everything we need, like save(), findById(), etc.
}