import axios from "axios";
import React from "react";
import {Button, Chip} from "@mui/material";

enum Cell { X = 'X', O = 'O', Empty = 'Empty' }

enum Player { X = 'X', O = 'O' }

type TicTacToeMove = {
    player: Player,
    board: Cell[][],
    winner: Player | null,
    valid: boolean,
    draw: boolean,
    gameOver: boolean,
}

const newGame = () => axios.get('http://localhost:8080/TicTacToeGame').then(res => res.data);
const makeMove = (i: number, j: number) => axios.post(`http://localhost:8080/TicTacToeMove/${i}/${j}`).then(res => res.data);

export const App = () => {
    const [move, setMove] = React.useState<TicTacToeMove | null>(null);

    React.useEffect(() => void newGame().then(setMove), []);

    const Square = ({cell, row, col}: { cell: Cell, row: number, col: number }) => {
        return <Button
            onClick={() => cell === Cell.Empty && makeMove(row, col).then(setMove)}
            color={cell == Cell.X ? 'error' : cell == Cell.O ? 'success' : 'primary'}
            style={{width: '150px', height: '150px', fontSize: "30px"}}
            variant="contained">
            {cell == Cell.X ? 'X' : cell == Cell.O ? 'O' : '-'}
        </Button>;
    }

    const Outcome = ({winner}: { winner: Player | null }) => <>  {}
        <Chip label={winner === null ? 'Draw' : winner === Player.X ? 'X wins' : 'O wins'}
              color={winner === null ? 'default' : winner === Player.X ? 'error' : 'success'}
              variant="outlined"
              style={{margin: '20px', fontSize: "30px"}}/>
        <Button
            onClick={() => newGame().then(setMove)}
            color="primary"
            variant="contained"
            style={{margin: '20px'}}>
            New Game
        </Button>
    </>

    const CurrentPlayer = ({player}: { player: Player }) =>
        <Chip label={player === Player.X ? '   X   ' : '   O   '}
              color={player === Player.X ? 'error' : 'success'}
              variant="outlined"
              style={{margin: '20px', fontSize: "30px"}}/>

    if (!move)
        return <div>Loading...</div>;

    return <div style={{textAlign: "center"}}> {}
        <div>
            <Square cell={move.board[0][0]} row={0} col={0}/>
            <Square cell={move.board[0][1]} row={0} col={1}/>
            <Square cell={move.board[0][2]} row={0} col={2}/>
        </div>
        <div>
            <Square cell={move.board[1][0]} row={1} col={0}/>
            <Square cell={move.board[1][1]} row={1} col={1}/>
            <Square cell={move.board[1][2]} row={1} col={2}/>
        </div>
        <div>
            <Square cell={move.board[2][0]} row={2} col={0}/>
            <Square cell={move.board[2][1]} row={2} col={1}/>
            <Square cell={move.board[2][2]} row={2} col={2}/>
        </div>

        <div>
            {move.gameOver && <Outcome winner={move.winner}/>}
        </div>

        <div>
            <CurrentPlayer player={move.player}/>
        </div>

    </div>
};