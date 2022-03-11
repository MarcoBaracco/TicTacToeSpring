package com.example.TicTacToe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicTacToeController {

    private final TicTacToeRepository ticTacToeRepository;

    public TicTacToeController(TicTacToeRepository ticTacToeRepository) {
        this.ticTacToeRepository = ticTacToeRepository;
    }

    //print su localhost di qualcosa
    @GetMapping("/TicTacToeGame")
    public String getGameStat() {
        return "Lo stato del gioco è questo: il nulla";
    }

    //return di un oggetto,che ha id null perchè giustamente sta solo leggendo
    @GetMapping("/TicTacToeMove")
    public TicTacToe makeMove() {
        return new TicTacToe("E,E,E,E,E,E,E,E,E", Player.X);
    }

    //salvataggio dell'oggetto su db
    @PostMapping("/TicTacToe/Create")
    public TicTacToe saveMove() {
        TicTacToe move = new TicTacToe("E,E,E,E,E,E,E,E,E", Player.X);
        return ticTacToeRepository.save(move);
    }
}
