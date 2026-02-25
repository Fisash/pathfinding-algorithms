import java.util.List;

class PFConsoleRenderer
{
    private ConsoleRenderFrameOptions frame;
    private final int msDisplayStateDelay; 
    private PFConsoleRenderCell[][] render;

    PFConsoleRenderer(int msDisplayStateDelay, ConsoleRenderFrameOptions frame)
    {
        this.msDisplayStateDelay = msDisplayStateDelay; 
        this.frame = frame;
    }

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

    public void stateHandle
        (Map map, int [][] distances, List<Point> statePath)
    {
        clear();

        int cellWidth = getCellWidth(map);

        initRender(map);
        renderMap(map);
        renderDistances(distances);
        renderPath(map, statePath);
        System.out.print(renderToString(cellWidth));

        try
        {
            Thread.sleep(msDisplayStateDelay); 
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
        }
    }

    public void draw(Map map, List<Point> path)
    {
        initRender(map);
        renderMap(map);
        renderPath(map, path);
        System.out.print("\n"); 
        System.out.print(renderToString(1));
    } 

    private String renderToString (int cellWidth)
    {
        StringBuilder result = new StringBuilder();
        int i, j;
        int lineLength = render[0].length;

        for(i = 0; i < lineLength*cellWidth*3/2; i++)
            result.append(frame.h_view);
        result.append("\n");

        for(i = 0; i < render.length; i++)
        {
            result.append(frame.v_view);
            for(j = 0; j < lineLength; j++)
            {
                result.append(render[i][j].stringView(cellWidth));
                result.append(" ");
            }
            result.append(frame.v_view);
            result.append("\n");
        }

        for(i = 0; i < 2*lineLength*cellWidth; i++)
            result.append(frame.h_view);
        result.append("\n");

        return result.toString();
    }

    private void renderPath
        (Map map, List<Point> path)
    {
        for (Point p : path)
        {
            if(!map.isInBounds(p))
                continue;
            render[p.y][p.x].isPath = true;
        } 
    }
    
    private void renderDistances (int[][] distances)
    {
        for(int i = 0; i < render.length; i++)
        {
            PFConsoleRenderCell[] renderLine = render[i];
            for(int j = 0; j < renderLine.length; j++)
                render[i][j].value = distances[i][j];
        }
    }

    private void renderMap(Map map)
    {
        for(int i = 0; i < render.length; i++)
        {
            PFConsoleRenderCell[] renderLine = render[i];
            for(int j = 0; j < renderLine.length; j++)
                render[i][j] = new PFConsoleRenderCell(map.getCell(j, i));
        }
    }
    
    private void initRender(Map map)
    {
        int width = map.getWidth();
        int height = map.getHeight();
        render = new PFConsoleRenderCell[height][width];
    }
}
