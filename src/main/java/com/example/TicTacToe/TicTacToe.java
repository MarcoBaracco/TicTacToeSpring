package com.example.TicTacToe;

import javax.persistence.*;
import java.util.Arrays;

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
        this(new GameLogic());
    }

    public TicTacToe(GameLogic gameLogic) {
        this.gameTable = gameLogic.gameTableSerialized(gameLogic);
        this.currentPlayer = gameLogic.currentPlayer;
    }

    public CellStatus[][] getGameTableMatrix(TicTacToe ticTacToe) {
        var gameT = Arrays.stream(ticTacToe.gameTable.split(";"))
                .map(x -> Arrays.stream(x.split(","))
                        .map(CellStatus::valueOf)
                        .toArray(CellStatus[]::new))
                .toArray(CellStatus[][]::new);
        return gameT;
    }

    public Long getId() {
        return id;
    }
}
