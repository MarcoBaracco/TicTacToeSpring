package com.example.TicTacToe;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameLogic {

    public CellStatus[][] gameTable = new CellStatus[3][3];
    public Player currentPlayer;

    GameLogic() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                gameTable[i][j] = CellStatus.EMPTY;

        currentPlayer = Player.X;

    }

    GameLogic(TicTacToe ticTacToe) {
        this.currentPlayer = ticTacToe.currentPlayer;
        this.gameTable = ticTacToe.getGameTableMatrix(ticTacToe);
    }

    static private boolean isWinning(CellStatus c0, CellStatus c1, CellStatus c2) {
        return c0 != CellStatus.EMPTY && c0 == c1 && c1 == c2;
    }

    static private Optional<Player> getWinner(CellStatus cell) {
        return Optional.of(cell == CellStatus.X ? Player.X : Player.O);
    }

    public void makeMove(int i, int j) throws InvalidTicTacToeInput {
        if (i < 0 || i > 2 || j < 0 || j > 2) {
            throw new InvalidTicTacToeInput("Out of Bounds");
        }
        if (gameTable[i][j] != CellStatus.EMPTY) {
            throw new InvalidTicTacToeInput("Position already used");
        }

        gameTable[i][j] = currentPlayer == Player.X ? CellStatus.X : CellStatus.O;
        currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
    }

    public Optional<Player> getTheWinner() {

        var g = this.gameTable;

        if (isWinning(g[0][0], g[0][1], g[0][2])) return getWinner(g[0][0]);
        if (isWinning(g[1][0], g[1][1], g[1][2])) return getWinner(g[1][0]);
        if (isWinning(g[2][0], g[2][1], g[2][2])) return getWinner(g[2][0]);


        if (isWinning(g[0][0], g[1][0], g[2][0])) return getWinner(g[0][0]);
        if (isWinning(g[0][1], g[1][1], g[2][1])) return getWinner(g[0][1]);
        if (isWinning(g[0][2], g[1][2], g[2][2])) return getWinner(g[0][2]);


        if (isWinning(g[0][0], g[1][1], g[2][2])) return getWinner(g[0][0]);
        if (isWinning(g[0][2], g[1][1], g[2][0])) return getWinner(g[0][2]);

        return Optional.empty();
    }

    public boolean isDraw() {
        for (var row : gameTable)
            for (var l : row)
                if (l == CellStatus.EMPTY)
                    return false;

        return true;
    }

    public boolean isMoveCorrect(int i, int j) {
        return gameTable[i][j] == CellStatus.EMPTY;
    }

    public boolean isGameOver() {
        return getTheWinner().isPresent() || isDraw();
    }

    public String gameTableSerialized(GameLogic gameLogic) {
        return Arrays.stream(gameLogic.gameTable)
                .map(x -> Arrays.stream(x).map(Enum::toString).collect(Collectors.joining(",")))
                .collect(Collectors.joining(";"));
    }


    class InvalidTicTacToeInput extends RuntimeException {
        InvalidTicTacToeInput(String msg) {
            super(msg);
        }
    }

}
