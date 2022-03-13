package com.example.TicTacToe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicTacToeController {

    private final TicTacToeRepository ticTacToeRepository;

    public TicTacToeController(TicTacToeRepository ticTacToeRepository) {
        this.ticTacToeRepository = ticTacToeRepository;
    }

    @GetMapping("/TicTacToeGame")
    public GameLogic newGame() {
        return new GameLogic(ticTacToeRepository.save(new TicTacToe()));
    }

    @PostMapping("/TicTacToeMove/{i}/{j}")
    public GameLogic makeMove(@PathVariable Integer i, @PathVariable Integer j) {
        var lastGame = ticTacToeRepository.findTopByOrderByIdDesc();


        if (lastGame.isEmpty()) throw new IllegalArgumentException("No Game here, honey! Start one first!");

        var startGame = new GameLogic(lastGame.get());

        if (!startGame.isMoveCorrect(i, j))
            throw new IllegalArgumentException("The move is not correct! Make another!");
        if (startGame.isGameOver()) throw new IllegalArgumentException("Game over! Thanks for playing!");

        startGame.makeMove(i, j);
        ticTacToeRepository.save(new TicTacToe(startGame));
        return startGame;

    }
}



