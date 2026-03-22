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

    public void run(){
        try {
            config = new Config("configs/pf.conf");
        }
        catch (IOException e){
            System.err.println("Error of read config");
            return;
        }

        map = new Map(config.MAP_FILE_PATH);

        if (map == null){
            System.err.println("Error of create map by file");
            return;
        }

        ConsoleRenderFrameOptions baseFrame = 
        new ConsoleRenderFrameOptions('═', '║', new char[]{'╔', '╗', '╚', '╝'});

        Renderer renderer = new PFConsoleRenderer(config.rendererStateDelay, baseFrame, map);

        createPF();

        if (pathFinder == null){
            System.err.println("Error of create PF object");
            return;
        }

        PathFindingResult result = pathFinder.search(map, config.start, config.finish, renderer);
        List<Point> path = result.path;

        if (path == null){
            System.err.println("Path is not found!");
            return;
        }

        System.out.println("Path found by " + config.algorithm.name());
        System.out.println(result.toString());
        renderer.draw(map, path);
    }

    public static void main(String[] args){
        new Main().run();
    }
}
