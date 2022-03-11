package com.example.TicTacToe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicTacToeRepository extends JpaRepository<TicTacToe, Long> {
    //devo dichiarare il metodo a spring e poi farà da solo
    Optional<TicTacToe> findTopByOrderByIdDesc();
}
