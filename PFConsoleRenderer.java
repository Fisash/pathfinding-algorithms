import java.util.List;

class PFConsoleRenderer
{
    private static final int msDisplayStateDelay = 100; 

    public static void clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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

        PFConsoleRenderCell[][] render = initRender(map);
        renderMap(map, render);
        renderDistances(render, distances);
        renderPath(map, render, statePath);
        System.out.print(renderToString(render, cellWidth));

        try
        {
            Thread.sleep(msDisplayStateDelay); 
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        }
    }

    public static String printPath(Map map, List<Point> path)
    {
        PFConsoleRenderCell[][] render = initRender(map);
        renderMap(map, render);
        renderPath(map, render, path);
        
        return renderToString(render, 1);
    } 

    private static String renderToString (PFConsoleRenderCell[][] render, int cellWidth)
    {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < render.length; i++)
        {
            PFConsoleRenderCell[] renderLine = render[i];
            for(int j = 0; j < renderLine.length; j++)
            {
                result.append(render[i][j].stringView(cellWidth));
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    private static void renderPath
        (Map map, PFConsoleRenderCell[][] render, List<Point> path)
    {
        for (Point p : path)
        {
            if(!map.isInBounds(p))
                continue;
            render[p.y][p.x].isPath = true;
        } 
    }
    
    private static void renderDistances (PFConsoleRenderCell[][] render, int[][] distances)
    {
        for(int i = 0; i < render.length; i++)
        {
            PFConsoleRenderCell[] renderLine = render[i];
            for(int j = 0; j < renderLine.length; j++)
                render[i][j].value = distances[i][j];
        }
    }

    private static void renderMap(Map map, PFConsoleRenderCell[][] render)
    {
        for(int i = 0; i < render.length; i++)
        {
            PFConsoleRenderCell[] renderLine = render[i];
            for(int j = 0; j < renderLine.length; j++)
                render[i][j] = new PFConsoleRenderCell(map.getCell(j, i));
        }
    }
    
    private static PFConsoleRenderCell[][] initRender(Map map)
    {
        int width = map.getWidth();
        int height = map.getHeight();
        return new PFConsoleRenderCell[height][width];
    }
}
