package com.test.assignments;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App {
    //Coffee Machine
    //Type of Coffee
    //1. User should be able to view menu of available coffee flavours
    //2. User should be able to select dosage for, and ask for disposal
    //3. Coffee machine should be able to verify if its on low inventory, and show appropriate warning for it
    public static void main( String[] args )
    {
        //System.out.println( "Hello World!" );
        App app = new App();
        app.testCommonOcean();
    }

    private void testCommonOcean() {
        int[][] island = {{3,3,3,3,3,3}, {3,0,3,3,0,3}, {3,3,3,3,3,3}};
        List<List<Integer>> common = pacificAtlantic(island);
        String res = common.stream().flatMap(e -> e.stream()).map(String::valueOf).collect(Collectors.joining(","));
        System.out.printf("Res: [%s]\n", res);
    }


    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        Set<Integer> poCells = fetchCells(0, 0, heights);
        Set<Integer> aoCells = fetchCells(m-1, n-1, heights);
        poCells.retainAll(aoCells);
        List<List<Integer>> res = new ArrayList<>();
        for(int commonCell: poCells) {
            res.add(Arrays.asList(commonCell/m, commonCell%m));
        }
        return res;
    }
    int[] DIRS = {-1, 0, 1, 0, -1};
    private Set<Integer> fetchCells(int initRow, int initCol, int[][] heights) {
        int m = heights.length, n = heights[0].length;
        Deque<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];
        for(int i = 0; i < m; i++) {
            queue.offer(new int[]{i, initCol});
            visited[i][initCol] = true;
        }
        for(int i = 0; i < n; i++) {
            queue.offer(new int[]{initRow, i});
            visited[initRow][i] = true;
        }
        Set<Integer> result = new HashSet<>();

        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            result.add(curr[0]*m + curr[1]);
            for(int i = 0; i < 4; i++) {
                int x = curr[0] + DIRS[i], y = curr[1] + DIRS[i+1];
                if(x < 0 || x  >= m
                        || y < 0 || y >= n
                        || visited[x][y]
                        || heights[curr[0]][curr[1]] > heights[x][y]) {
                    continue;
                }
                visited[x][y] = true;
                queue.offer(new int[]{x, y});
            }
        }
        return result;
    }
}
