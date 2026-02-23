import java.util.List;

class PathConsoleVisualizer
{
    public static String printPath(Map map, List<Point> path)
    {
        String mapString = printMap(map);
        String[] lines = mapString.split("\n", -1);
        
        String[][] grid = new String[lines.length][];
        
        for (int i = 0; i < lines.length; i++)
            grid[i] = lines[i].trim().split("\\s+");

        for (Point p : path)
        {
            if(!map.isInBounds(p))
                continue;
            grid[p.y][p.x] = "$";
        } 
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < grid.length; i++)
        {
            result.append(String.join(" ", grid[i]));
            result.append("\n");
        }
        return result.toString();
    } 

    public static String printMap(Map map)
    {
        String result = "";
        for (int i = 0; i < map.getHeight(); i++)
        {
            for(int j = 0; j < map.getWidth(); j++)
            {
                result += map.getCell(j, i);
                result += " ";
            }
            result += "\n";
        }
        return result;
    } 
}
