package com.twisha;

public class Game {

    private Player[] players;
    private int playerIdx;

    private Board board;



    public Game() {
        players = new Player[2];
        players[0] = new Player(1, Color.WHITE, new BestMoveStrategy());
        players[1] = new Player(2, Color.BLACK, new BestMoveStrategy());
        playerIdx = 0;
        this.board = new Board();
    }

    public void togglePlayers() {
        this.playerIdx = 1 - this.playerIdx;
    }

    public void playGame() {
        while(true) {
            Player currPlayer = this.players[playerIdx];
            Cell[] nextSuggestion = currPlayer.suggestNextMove(board.getPositionMap());
            if(isWinningMove(nextSuggestion[1], currPlayer)) {
                System.out.printf("Player: %d won the match %d\n", currPlayer.getId());
                break;
            }
            currPlayer.makeNextMove(nextSuggestion[0], nextSuggestion[1]);
        }
    }

    private boolean isWinningMove(Cell cell, Player currPlayer) {
        return currPlayer.getPieces().stream()
                .anyMatch(piece -> piece instanceof King && piece.getPosition().equals(cell));
    }
}
