package com.test.assignments;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    // Write a function that accepts a
    // Map<String, Map<Long, Integer>> and returns Map<Long, Integer>
    // such that Integer values are summed together for the same Long values regardless of String key in the outer map.


    /***
     * "key1": {
     *     1l: 2,
     *     2l: 4
     * },
     * "key2": {
     *     3l: 5,
     *     2l: 4
     * }
     * Return {
     *     1l: 2,
     *     2l: 8,
     *     3l: 5
     * }
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Main main = new Main();
        main.testIncrementReturn();
    }

    private void testIncrementReturn() {
        Function<Integer, Integer> funct = a -> a+5;
        Function<Integer, Integer> res = incrementReturn(funct);
        int b = res.apply(10);
        System.out.printf("Final value: %d\n", b);
    }

    private Map<Long, Integer>  aggregateValues(Map<String, Map<Long, Integer>> input) {
        return input.values().stream()
                .flatMap(e -> e.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum, ConcurrentHashMap::new));
    }

    // Write a function that accepts a Function<Integer, Integer> as a parameter and
    // returns a Function<Integer, Integer> that increments the returned int by 10

    private Function<Integer, Integer> incrementReturn(Function<Integer, Integer> funct) {
       return a -> funct.apply(a) + 10;
    }
}