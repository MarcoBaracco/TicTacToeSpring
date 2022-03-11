package com.example.TicTacToe;

import javax.persistence.*;

enum CellStatus {EMPTY, X, O}

enum Player {X, O}


@Entity
public class TicTacToe {
    public String gameTable;
    public Player currentPlayer;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public TicTacToe() {
    }

    public TicTacToe(GameLogic gameLogic) {
        this.gameTable = gameLogic.gameTableSerialized(gameLogic);
        this.currentPlayer = gameLogic.currentPlayer;
    }

    public Long getId() {
        return id;
    }
}
