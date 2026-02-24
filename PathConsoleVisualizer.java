import java.util.List;

class PathConsoleVisualizer
{
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m"; 

    private static String wrapRed(String input)
    {
        return RED+input+RESET;
    }

    private static final String FREE = "_";
    private static final String WALL = "#";
    private static final String PATHPOINT = "o";
    private static final String UNVISITED = "_";

    private static final int msDisplayStateDelay = 100; 

    private static String getMapCellDisplay(int cell)
    {
        if(cell == Map.FREE)
            return FREE;
        if(cell == Map.WALL)
            return WALL;
        return "N";
    }

    private static String formatCell(String content, int width)
    {
        int length = content.length();
        if (length >= width) 
            return content;

        int zerosToAdd = width - length;
        StringBuilder result = new StringBuilder(width);
        for (int i = 0; i < zerosToAdd; i++) {
            result.append('_');
        }
        result.append(content);
        return result.toString();
    }

    private static String getDistanceDisplay(int distance)
    {
        if(distance == -1)
            return UNVISITED;
        return String.valueOf(distance);
    }

    public static int getCellWidth(Map map)
    {
        int maxPossibleDist = map.getWidth() * map.getHeight();
        return String.valueOf(maxPossibleDist).length();
    }

    public static void stateHandle
        (Map map, int [][] distances, List<Point> statePath)
    {
        clear();

        int cellWidth = getCellWidth(map);
        String state = getDistancesState(distances, cellWidth);
        state = drawPathOverString(map, state, statePath, cellWidth); 
        System.out.print(state);

        try
        {
            Thread.sleep(msDisplayStateDelay); 
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        }
    }

    private static String getDistancesState(int[][] distances, int cellWidth)
    {
        String result = "";
        for(int i = 0; i < distances.length; i++)
        {
            int[] distancesString = distances[i];
            for(int j = 0; j < distancesString.length; j++)
            {
                String cellDisplay = getDistanceDisplay(distances[i][j]);
                result += formatCell(cellDisplay, cellWidth);
                result += " ";
            }
            result += "\n";
        }
        return result;
    }

    public static void clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String drawPathOverString
        (Map map, String mapString, List<Point> path, int cellWidth)
    {
        String[] lines = mapString.split("\n", -1);
        String[][] grid = new String[lines.length][];
        
        for (int i = 0; i < lines.length; i++)
            grid[i] = lines[i].trim().split("\\s+");

        for (Point p : path)
        {
            if(!map.isInBounds(p))
                continue;
            grid[p.y][p.x] = wrapRed(formatCell(PATHPOINT, cellWidth));
        } 
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < grid.length; i++)
        {
            result.append(String.join(" ", grid[i]));
            result.append("\n");
        }
        return result.toString();
    }

    public static String printPath(Map map, List<Point> path)
    {
        String mapString = printMap(map, 1);
        return drawPathOverString(map, mapString, path, 1);
    } 

    public static String printMap(Map map, int cellWidth)
    {
        String result = "";
        for (int i = 0; i < map.getHeight(); i++)
        {
            for(int j = 0; j < map.getWidth(); j++)
            {
                String cellDisplay = getMapCellDisplay(map.getCell(j, i));
                result += formatCell(cellDisplay, cellWidth);
                result += " ";
            }
            result += "\n";
        }
        return result;
    } 
}
