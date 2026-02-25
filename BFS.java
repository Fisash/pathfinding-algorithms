import java.util.*;

class BFS
{
    private static int[][] distances;
    private static Queue<Point> queue;
    private static final int[][] directions = 
    {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};     
    private static Point[][] ancestors;

    private static boolean isNoWayAPriori(Map map, Point start, Point finish)
    {
        boolean isStartWall = map.getCell(start) == Map.WALL;
        boolean isFinishWall = map.getCell(finish) == Map.WALL;

        return isStartWall || isFinishWall;
    }
    
    private static void init(Map map)
    {
        int width = map.getWidth();
        int height = map.getHeight();

        //distances from start to every cell. -1 if unvisited
        distances = new int[height][width];
        for (int i = 0; i < height; i++)
            Arrays.fill(distances[i], -1);
        
        //ancestors[x][y] - coord of cell from we came to (x, y) cell 
        ancestors = new Point[height][width];

        queue = new LinkedList<>();        
    }

    private static List<Point> buildPath (Point start, Point finish)
    {
        List<Point> result = new ArrayList<>();
        Point current = finish;
        while (current != null)
        {
            result.add(current);
            if(current.equals(start))
                break;
            current = ancestors[current.y][current.x];
        }    
        Collections.reverse(result);
        return result;
    } 

    public static List<Point> search
    (Map map, Point start, Point finish, PFConsoleRenderer intermediateResRenderer)
    {
        if(isNoWayAPriori(map, start, finish))
            return null; 
        
        init(map);

        queue.add(start);
        distances[start.y][start.x] = 0; //visited start cell
        
        while (!queue.isEmpty())
        {
            Point cell = queue.poll();
            if (cell.equals(finish))
                return buildPath(start, finish); 

            for (int i = 0; i < directions.length; i++)
            {
                int new_x = cell.x + directions[i][0];
                int new_y = cell.y + directions[i][1];
               
                Point newPoint = new Point(new_x, new_y); 

                if (!map.isInBounds(newPoint))
                    continue;
                if (map.getCell(newPoint) != Map.FREE)
                    continue;
                if (distances[new_y][new_x] != -1)
                    continue;

                distances[new_y][new_x] = distances[cell.y][cell.x] + 1;
                ancestors[new_y][new_x] = cell;
                queue.add(newPoint);
                if(intermediateResRenderer != null)
                    intermediateResRenderer.stateHandle(map, distances, buildPath(start, newPoint));
            }  
        }
        return null; //path is not found
    }    
}
