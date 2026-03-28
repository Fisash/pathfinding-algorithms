package com.example.console;

import com.example.Map;
import com.example.PathFinder;
import com.example.Config;
import com.example.BFS;
import com.example.AStar;
import com.example.Algorithm;
import com.example.Point;
import com.example.PathFindingResult;
import com.example.Renderer;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

class Main{

    private Map map;
    private PathFinder pathFinder; 

    private Config config;

    private void createPF(){
        switch (config.algorithm){
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

    public void run(String configPath){
        try {
            config = new Config(configPath);
        }
        catch (IOException e){
            System.err.println("Error of read config");
            return;
        }

        map = config.map;

        if (map == null){
            System.err.println("Error of create map by file");
            return;
        }

        ConsoleFrameOptions baseFrame = 
        new ConsoleFrameOptions('═', '║', new char[]{'╔', '╗', '╚', '╝'});

        Renderer renderer = new ConsoleRenderer(config.rendererStateDelay, baseFrame, map);

        createPF();

        if (pathFinder == null){
            System.err.println("Error of create PF object");
            return;
        }

        PathFindingResult result = pathFinder.find(map, config.start, config.finish, renderer);
        List<Point> path = result.path;

        if (path == null){
            System.err.println("Path is not found!");
            return;
        }

        renderer.endAnimation();
        renderer.drawPath(path);
        System.out.println("Path found by " + config.algorithm.name());
        System.out.println(result.toString());
    }

    public static void main(String[] args){
        if(args.length == 0)
            System.out.println("Choose config menu");
        else if (args.length == 1) {
            String configPath = args[0];
            new Main().run(configPath);
        }
        else 
          System.out.println("Too much arguments");
    }
}
