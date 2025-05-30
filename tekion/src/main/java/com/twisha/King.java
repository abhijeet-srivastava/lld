package com.twisha;

import java.util.List;
import java.util.Map;

public class King extends Piece {
    public King(Color color) {
        super(color);
    }

    @Override
    public List<Cell> nextAvailableMoves(Map<Cell, Piece> currPositionMap) {
        return null;
    }
}
