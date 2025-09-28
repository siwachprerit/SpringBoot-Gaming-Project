package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Tells Spring that this is a repository bean
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA is smart. It automatically creates a query for this
    // method just based on its name. It will find a user by their username.
    Optional<User> findByUsername(String username);

    // This is our leaderboard method. Spring will automatically create a
    // query to find the top 10 users, ordered by their balance in descending order.
    List<User> findTop10ByOrderByBalanceDesc();
}