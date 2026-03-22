package com.example;

public interface Renderer {
    public void draw(Map map, java.util.List<Point> path);

    public void stateHandle 
        (Map map, int [][] distances, java.util.List<Point> statePath);
}
