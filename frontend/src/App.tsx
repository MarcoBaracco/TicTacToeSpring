import axios from "axios";
import React from "react";
import {Button, Chip} from "@mui/material";

enum CellStatus { X = 'X', O = 'O', Empty = 'Empty' }

enum Player { X = 'X', O = 'O' }

type TicTacToe = {
    player: Player,
    gameTable: CellStatus[][],
    winner: Player | null,
    valid: boolean,
    draw: boolean,
    gameOver: boolean,
}

const newGame = () => axios.get('http://localhost:8080/TicTacToeGame').then(res => res.data);
const makeMove = (i: number, j: number) => axios.post(`http://localhost:8080/TicTacToeMove/${i}/${j}`).then(res => res.data);

export const App = () => {
    const [ticTacToe, setTicTacToe] = React.useState<TicTacToe | null>(null);

    React.useEffect(() => void newGame().then(setTicTacToe), []);

    const Square = ({cell, row, col}: { cell: CellStatus, row: number, col: number }) => {
        return <Button
            onClick={() => cell === CellStatus.Empty && makeMove(row, col).then(setTicTacToe)}
            color={cell == CellStatus.X ? 'error' : cell == CellStatus.O ? 'success' : 'primary'}
            style={{width: '150px', height: '150px', fontSize: "30px"}}
            variant="contained">
            {cell == CellStatus.X ? 'X' : cell == CellStatus.O ? 'O' : '-'}
        </Button>;
    }

    const Outcome = ({winner}: { winner: Player | null }) => <>  {}
        <Chip label={winner === null ? 'Draw' : winner === Player.X ? 'X wins' : 'O wins'}
              color={winner === null ? 'default' : winner === Player.X ? 'error' : 'success'}
              variant="outlined"
              style={{margin: '20px', fontSize: "30px"}}/>
        <Button
            onClick={() => newGame().then(setTicTacToe)}
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

    if (!ticTacToe)
        return <div>Loading...</div>;

    return <div style={{textAlign: "center"}}> {}
        <div>
            <Square cell={ticTacToe.gameTable[0][0]} row={0} col={0}/>
            <Square cell={ticTacToe.gameTable[0][1]} row={0} col={1}/>
            <Square cell={ticTacToe.gameTable[0][2]} row={0} col={2}/>
        </div>
        <div>
            <Square cell={ticTacToe.gameTable[1][0]} row={1} col={0}/>
            <Square cell={ticTacToe.gameTable[1][1]} row={1} col={1}/>
            <Square cell={ticTacToe.gameTable[1][2]} row={1} col={2}/>
        </div>
        <div>
            <Square cell={ticTacToe.gameTable[2][0]} row={2} col={0}/>
            <Square cell={ticTacToe.gameTable[2][1]} row={2} col={1}/>
            <Square cell={ticTacToe.gameTable[2][2]} row={2} col={2}/>
        </div>

        <div>
            {ticTacToe.gameOver && <Outcome winner={ticTacToe.winner}/>}
        </div>

        <div>
            <CurrentPlayer player={ticTacToe.player}/>
        </div>

    </div>
};