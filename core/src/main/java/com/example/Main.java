package com.example;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;

class Main{
    private enum Algorithm{
        BFS, AStar
    }

    private String MAP_FILE_PATH;

    private Point start;
    private Point finish;

    private int rendererStateDelay;    
    private Algorithm algorithm;

    private Map map;
    private PathFinder pathFinder; 

    private int loadProperties(){

        try(FileReader reader = new FileReader("pf.conf")){
            Properties props = new Properties();
            props.load(reader);

            MAP_FILE_PATH = props.getProperty("map_path");

            String[] startString = props.getProperty("start").split(";");
            int start_x = Integer.parseInt(startString[0].trim());
            int start_y = Integer.parseInt(startString[1].trim());

            String[] finishString = props.getProperty("finish").split(";");
            int finish_x = Integer.parseInt(finishString[0].trim());
            int finish_y = Integer.parseInt(finishString[1].trim());

            start = new Point(start_x, start_y);
            finish = new Point(finish_x, finish_y);

            rendererStateDelay = Integer.parseInt(props.getProperty("renderer_delay"));

            String algorithmString = props.getProperty("algorithm");
            algorithm = Enum.valueOf(Algorithm.class, algorithmString); 

        }
        catch (IOException e){
            return 1;
        }
        return 0;
    }

    private void createPF(){
        switch (algorithm){
            case BFS:
                pathFinder = new BFS();
                break;
            case AStar:
                pathFinder = new AStar();
                break;
            default:
                pathFinder = null;
        }
    }

    public void run(){
        if (loadProperties() == 1){
            System.err.println("Error of read config");
            return;
        }

        map = new Map(MAP_FILE_PATH);

        if (map == null){
            System.err.println("Error of create map by file");
            return;
        }

        ConsoleRenderFrameOptions baseFrame = 
        new ConsoleRenderFrameOptions('═', '║', new char[]{'╔', '╗', '╚', '╝'});

        PFConsoleRenderer renderer = new PFConsoleRenderer(rendererStateDelay, baseFrame, map);

        createPF();

        if (pathFinder == null){
            System.err.println("Error of create PF object");
            return;
        }

        PathFindingResult result = pathFinder.search(map, start, finish, renderer);
        List<Point> path = result.path;

        if (path == null){
            System.err.println("Path is not found!");
            return;
        }

        System.out.println("Path found by " + algorithm.name());
        System.out.println(result.toString());
        renderer.draw(map, path);
    }

    public static void main(String[] args){
        new Main().run();
    }
}
