package com.example;
import java.util.*;

class AStar extends PathFinder {
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
    
    private void initSpecific(){
        openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        for (int i = 0; i < height; i++)
            Arrays.fill(distances[i], Integer.MAX_VALUE);
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
    
    @Override
    public PathFindingResult search(Map map, Point start, Point finish, PFConsoleRenderer renderer) {
        if (isNoWayAPriori(map, start, finish)) 
            return null;
        initBase(map);
        initSpecific();
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
                    int f = newG + heuristic(neighbor, finish);
                    openSet.add(new Node(neighbor, newG, f));
                    iterationCount++;
                    if (renderer != null)
                        renderer.stateHandle(map, distances, buildPath(start, neighbor));
                }
            }
        }
        return null;
    }
}
