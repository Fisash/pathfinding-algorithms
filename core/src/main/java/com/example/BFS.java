package com.example;
import java.util.*;

public class BFS extends PathFinder {
    Queue<Point> queue;

    @Override
    protected void init(Map map){
        super.init(map);
        queue = new LinkedList<>();
    }

    @Override
    public PathFindingResult find (Map map, Point start, Point finish, Renderer renderer) {
        if (isNoWayAPriori(map, start, finish)) 
            return null;
        init(map);
        currentState.finish = finish;
        queue.add(start);
        distances[start.y][start.x] = 0;
        
        while (!queue.isEmpty()) {
            Point cell = queue.poll();
            if (cell.equals(finish)){
                List<Point> path = buildPath(start, finish); 
                return new PathFindingResult(path, path.size()-1, iterationCount);
            }
            int nx, ny;
            for (int[] dir : DIRECTIONS) {
                nx = cell.x + dir[0];
                ny = cell.y + dir[1];
                Point neighbor = new Point(nx, ny);
                if (!map.isSuitableToVisit(neighbor) || 
                    currentState.visited[ny][nx]) 
                    continue;
                distances[ny][nx] = distances[cell.y][cell.x] + 1;
                currentState.visited[ny][nx] = true;
                ancestors[ny][nx] = cell;
                queue.add(neighbor);
                iterationCount++;
                updateState(buildPath(start, neighbor));
                if (renderer != null)
                    renderer.update(currentState);
            }
        }
        return null;
    }
}
