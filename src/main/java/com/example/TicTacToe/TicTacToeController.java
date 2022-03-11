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

    //print su localhost di qualcosa
    //@GetMapping("/TicTacToeGame")
    //public String getGameStat() {
    //  return "Lo stato del gioco è questo: il nulla";
    //}

    //return di un oggetto,che ha id null perchè giustamente sta solo leggendo
    //@GetMapping("/TicTacToeMove")
    //public TicTacToe makeMove() {
    //  return new TicTacToe("E,E,E,E,E,E,E,E,E", Player.X);
    //}


    //salvataggio dell'oggetto su db
    /*@PostMapping("/TicTacToe/Create")
    public TicTacToe saveMove() {
        TicTacToe move = new TicTacToe("E,E,E,E,E,E,E,E,E", Player.X);
        return ticTacToeRepository.save(move);
    } */

    @GetMapping("/TicTacToeGame")
    public GameLogic newGame() {
        return new GameLogic(ticTacToeRepository.save(new TicTacToe()));
    }

    @PostMapping("/TicTacToeMove/{i}/{j}")
    public GameLogic makeMove(@PathVariable Integer i, @PathVariable Integer j) {
        var lastGame = ticTacToeRepository.findTopByOrderByIdDesc();

        //se non è ancora stato creato nulla e uso l'exception per dirlo
        if (lastGame.isEmpty()) throw new IllegalArgumentException("No Game here, honey! Start one first!");

        //qui uso il get perchè lastGame mi è ritornato come optional (un booleano) uso get per prenderne il valore
        //questo starta il game riprendendo dall'ultimo stato del game
        var startGame = new GameLogic(lastGame.get());

        // ! è il diverso se la mossa fatta con start game (checkata da is MoveCorrect) fai ...
        if (!startGame.isMoveCorrect(i, j)) throw new IllegalArgumentException("Move not correct try again");
        if (startGame.isGameOver()) throw new IllegalArgumentException("Game over! Thanks for playing!");

        //aggiorna la nuova mossa, poi sotto la salvo, e ritorno infine il gioco aggiornato perchè è in fondo e quindi prende l'ultimo valore
        startGame.makeMove(i, j);
        //dentro il save salva una nuova entity che creo con il costruttore secondario, il cui input è il game aggiornato prima con makeMove
        ticTacToeRepository.save(new TicTacToe(startGame));
        return startGame;

    }
}



