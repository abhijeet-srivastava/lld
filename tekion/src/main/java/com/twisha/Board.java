package com.twisha;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private static final Character ROW_START = 'A';
    private static final Character ROW_END = 'H';

    private static final Integer COL_START = 1;
    private static final Integer COL_END = 8;


    private  final Cell[][] board;

    private Map<Cell, Piece> positionMap;

    public Board() {
        this.board = new Cell[8][8];
        for(char row = ROW_START; row <= ROW_END; row++) {
            for(int col = COL_START; col <= COL_END; col++) {
                board[(row- 'A') + 1][col-1] = new Cell(row, col);
            }
        }
        this.positionMap = initializeBoard();
    }

    private HashMap<Cell, Piece> initializeBoard() {
        HashMap<Cell, Piece> posMap = new HashMap<>();
        // TODO: Initialize
        return posMap;
    }

    public Map<Cell, Piece> getPositionMap() {
        return positionMap;
    }
}
