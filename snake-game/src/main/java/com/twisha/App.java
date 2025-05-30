package com.twisha;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Playing snake game:" );

        Game game = new Game(10, 10, new int[]{4,5},
                Arrays.asList(new int[]{2,3}, new int[]{5,6}, new int[]{7,8}, new int[]{1,4}));
        game.playGame();
    }
}
