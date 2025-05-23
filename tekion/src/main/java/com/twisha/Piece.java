package com.twisha;

import java.util.List;
import java.util.Map;

public abstract class Piece {
    private final Color color;
    private Cell position;

    public abstract List<Cell> nextAvailableMoves(Map<Cell, Piece> currPositionMap);

    public Piece(Color color) {
        this.color = color;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }
}
