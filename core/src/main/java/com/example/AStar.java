package com.example;
import java.util.*;

public class AStar extends PathFinder {
    private class Node {
        Point point;
        int g;
        int f;
        Node(Point p, int g, int f) {
            this.point = p;
            this.g = g;
            this.f = f;
        }
    }
    
    PriorityQueue<Node> openSet;
    
    @Override
    protected void init(Map map){
        super.init(map);
        openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        for (int i = 0; i < height; i++)
            Arrays.fill(distances[i], Integer.MAX_VALUE);
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    
    @Override
    public PathFindingResult find(Map map, Point start, Point finish, Renderer renderer) {
        if (isNoWayAPriori(map, start, finish)) 
            return null;
        init(map);
        currentState.finish = finish;
        distances[start.y][start.x] = 0;
        openSet.add(new Node(start, 0, heuristic(start, finish)));
        
        while (!openSet.isEmpty()) {
            Node node = openSet.poll();
            Point cell = node.point;
            if (node.g > distances[cell.y][cell.x]) 
                continue; 
            if (cell.equals(finish)){ 
                List<Point> path = buildPath(start, finish);
                return new PathFindingResult(path, path.size()-1, iterationCount);
            }

            int nx, ny, newG;
            for (int[] dir : DIRECTIONS) {
                nx = cell.x + dir[0];
                ny = cell.y + dir[1];
                Point neighbor = new Point(nx, ny);
                if (!map.isSuitableToVisit(neighbor)) 
                    continue;
                newG = distances[cell.y][cell.x] + 1;
                if (newG < distances[ny][nx]) {
                    ancestors[ny][nx] = cell;
                    distances[ny][nx] = newG;
                    currentState.visited[ny][nx] = true;
                    int f = newG + heuristic(neighbor, finish);
                    openSet.add(new Node(neighbor, newG, f));
                    iterationCount++;
                    updateState(buildPath(start, neighbor));
                    if (renderer != null)
                        renderer.update(currentState);
                }
            }
        }
        return null;
    }
}
