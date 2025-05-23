package com.twisha;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {

    private final Integer id;
    private final Color color;

    private List<Piece> pieces;

    private  final NextMoveStrategy strategy;

    public Player(Integer id, Color color, NextMoveStrategy strategy) {
        this.id = id;
        this.color = color;
        this.pieces = new ArrayList<>();
        this.strategy = strategy;
    }

    public Cell[] suggestNextMove(Map<Cell, Piece> positions) {
        return this.strategy.nextMoveStrategy(positions, this.color);
    }

    public void makeNextMove(Cell source, Cell target) {

    }

    public Integer getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public NextMoveStrategy getStrategy() {
        return strategy;
    }
}
