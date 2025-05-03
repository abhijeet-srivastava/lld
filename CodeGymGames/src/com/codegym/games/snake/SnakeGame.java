package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    @Override
    public void initialize() {

    }

    public void setScreenSize() {
        super.setScreenSize(WIDTH, HEIGHT);
    }
}
