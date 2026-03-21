package com.example;
import java.util.*;

class PathFindingResult{
    List<Point> path;
    int length;
    int iterationCount;  
    
    PathFindingResult(List<Point> path, int length, int iterationCount){
        this.path = path;
        this.length = length;
        this.iterationCount = iterationCount; 
    }

    public String toString() {
        return "Length: " + length + "; Iteration count: " + iterationCount;
    }
}
