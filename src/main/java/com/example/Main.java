package com.example;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;

class Main
{
    private static String MAP_FILE_PATH;
    private static Point start;
    private static Point finish;
    private static int rendererStateDelay;    

    private static int loadProperties()
    {

        try(FileReader reader = new FileReader("config.properties"))
        {
            Properties props = new Properties();
            props.load(reader);

            MAP_FILE_PATH = props.getProperty("map.path");

            int start_x = Integer.parseInt(props.getProperty("start.x"));
            int start_y = Integer.parseInt(props.getProperty("start.y"));
            int finish_x = Integer.parseInt(props.getProperty("finish.x"));
            int finish_y = Integer.parseInt(props.getProperty("finish.y"));

            start = new Point(start_x, start_y);
            finish = new Point(finish_x, finish_y);

            rendererStateDelay = Integer.parseInt(props.getProperty("renderer.delay"));

        }
        catch (IOException e)
        {
            return 1;
        }
        return 0;
    }

    public static void main(String[] args)
    {

        if (loadProperties() == 1)
        {
            System.err.println("Error of read config.properties");
            return;
        }

        Map map = new Map(MAP_FILE_PATH);

        if (map == null)
        {
            System.err.println("Error of create map by file");
            return;
        }

        ConsoleRenderFrameOptions baseFrame = 
        new ConsoleRenderFrameOptions('═', '║', new char[]{'╔', '╗', '╚', '╝'});

        PFConsoleRenderer renderer = new PFConsoleRenderer(rendererStateDelay, baseFrame, map);

        List<Point> path = BFS.search(map, start, finish, renderer);

        if (path == null)
        {
            System.err.println("Path is not found!");
            return;
        }

        System.out.println("Path found by BFS:");
        renderer.draw(map, path);
    }
}
