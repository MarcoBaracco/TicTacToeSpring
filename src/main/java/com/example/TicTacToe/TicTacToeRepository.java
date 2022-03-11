package com.example.TicTacToe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicTacToeRepository extends JpaRepository<TicTacToe, Long> {
}
