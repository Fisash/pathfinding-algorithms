package com.example;
import java.util.List;

class PFConsoleRenderer
{
    private ConsoleRenderFrameOptions frame;
    private final int msDisplayStateDelay; 
    private PFConsoleRenderCell[][] render;

    private boolean isHideDistancesInResult = true;

    PFConsoleRenderer(int msDisplayStateDelay, ConsoleRenderFrameOptions frame, Map initMap)
    {
        this.msDisplayStateDelay = msDisplayStateDelay; 
        this.frame = frame;
        initRender(initMap);
        renderMap(initMap);
    }

    public static void clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int evaluateCellWidth(Map map)
    {
        int maxPossibleDist = map.getWidth() * map.getHeight();
        return String.valueOf(maxPossibleDist).length();
    }

    public void stateHandle
        (Map map, int [][] distances, List<Point> statePath)
    {
        clear();

        int cellWidth = evaluateCellWidth(map);

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
        if(isHideDistancesInResult)
            clearDistances();
        renderPath(map, path);
        int cellWidth = isHideDistancesInResult ? 1 : evaluateCellWidth(map);
        System.out.print(renderToString(cellWidth));
    } 

    private String renderToString (int cellWidth)
    {
        StringBuilder result = new StringBuilder();
        int i, j;
        int lineLength = render[0].length;
        frame.addTopLine(result, lineLength, cellWidth);
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

        frame.addBottomLine(result, lineLength, cellWidth);
        return result.toString();
    }

    private void renderPath
        (Map map, List<Point> path)
    {
        for(int i = 0; i < render.length; i++)
        {
            for(int j = 0; j < render[i].length; j++)
                render[i][j].isPath = false;
        }

        for (Point p : path)
        {
            if(!map.isInBounds(p))
                continue;
            render[p.y][p.x].isPath = true;
        } 
    }
    
    private void clearDistances ()
    {
        for(int i = 0; i < render.length; i++)
        {
            for(int j = 0; j < render[i].length; j++)
                render[i][j].value = -1;
        }
    }
    
    private void renderDistances (int[][] distances)
    {
        for(int i = 0; i < render.length; i++)
        {
            for(int j = 0; j < render[i].length; j++)
                render[i][j].value = distances[i][j];
        }
    }

    private void renderMap(Map map)
    {
        for(int i = 0; i < render.length; i++)
        {
            for(int j = 0; j < render[i].length; j++)
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
