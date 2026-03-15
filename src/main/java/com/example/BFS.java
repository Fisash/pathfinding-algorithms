package com.example;
import java.util.*;

class BFS extends PathFinder {
    @Override
    public List<Point> search(Map map, Point start, Point finish, PFConsoleRenderer renderer) {
        if (isNoWayAPriori(map, start, finish)) return null;
        initArrays(map);
        for (int i = 0; i < height; i++)
            Arrays.fill(distances[i], -1);
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        distances[start.y][start.x] = 0;
        
        while (!queue.isEmpty()) {
            Point cell = queue.poll();
            if (cell.equals(finish))
                return buildPath(start, finish);
            for (int[] dir : DIRECTIONS) {
                int nx = cell.x + dir[0];
                int ny = cell.y + dir[1];
                Point neighbor = new Point(nx, ny);
                if (!map.isInBounds(neighbor)) continue;
                if (map.getCell(neighbor) != Map.FREE) continue;
                if (distances[ny][nx] != -1) continue;
                
                distances[ny][nx] = distances[cell.y][cell.x] + 1;
                ancestors[ny][nx] = cell;
                queue.add(neighbor);
                if (renderer != null)
                    renderer.stateHandle(map, distances, buildPath(start, neighbor));
            }
        }
        return null;
    }
}
