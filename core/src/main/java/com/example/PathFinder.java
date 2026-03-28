package com.example;
import java.util.*;

public abstract class PathFinder {
    protected final int[][] DIRECTIONS = {
        {1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}
    };
    
    protected int[][] distances;
    protected Point[][] ancestors;
    protected int width, height;

    protected PathFindingState currentState;
    
    protected int iterationCount;
    
    protected void init(Map map) {
        width = map.getWidth();
        height = map.getHeight();
        distances = new int[height][width];
        ancestors = new Point[height][width];

        currentState = new PathFindingState();
        currentState.visited = new boolean[height][width];
        currentState.values = distances;

        iterationCount = 0;
    }

    protected void updateState(List<Point> currentPath) {
        currentState.values = distances;
        currentState.currentPath = currentPath;
    }

    protected List<Point> buildPath(Point start, Point finish) {
        List<Point> path = new ArrayList<>();
        Point current = finish;
        while (current != null) {
            path.add(current);
            if (current.equals(start)) break;
            current = ancestors[current.y][current.x];
        }
        Collections.reverse(path);
        return path;
    }
    
    protected boolean isNoWayAPriori(Map map, Point start, Point finish) {
        return map.getCell(start) == Map.WALL || map.getCell(finish) == Map.WALL;
    }
    
    public abstract PathFindingResult find(Map map, Point start, Point finish, Renderer renderer);
}
