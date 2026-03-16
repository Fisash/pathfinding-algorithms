package com.example;
import java.util.*;

class BFS extends PathFinder {

    Queue<Point> queue;
    private void initSpecific(){
        queue = new LinkedList<>();
        for (int i = 0; i < height; i++)
            Arrays.fill(distances[i], -1); //-1 distance is not visited cell
    }

    @Override
    public PathFindingResult search (Map map, Point start, Point finish, PFConsoleRenderer renderer) {
        if (isNoWayAPriori(map, start, finish)) 
            return null;
        initBase(map);
        initSpecific();
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
                if (!map.isSuitableToVisit(neighbor) || distances[ny][nx] != -1) 
                    continue;
                distances[ny][nx] = distances[cell.y][cell.x] + 1;
                ancestors[ny][nx] = cell;
                queue.add(neighbor);
                iterationCount++;
                if (renderer != null)
                    renderer.stateHandle(map, distances, buildPath(start, neighbor));
            }
        }
        return null;
    }
}
