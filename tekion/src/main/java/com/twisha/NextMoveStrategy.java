package com.twisha;

import java.util.Map;

public interface NextMoveStrategy {

    Cell[] nextMoveStrategy(Map<Cell, Piece> positionMap, Color currPlayerColr);
}
