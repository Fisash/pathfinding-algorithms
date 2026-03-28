package com.example;

import java.util.List;

public class PathFindingState {
    public boolean[][] visited;
    public boolean[][] openSet;
    public int[][] values;
    public List<Point> currentPath;
    public Point finish;
}
