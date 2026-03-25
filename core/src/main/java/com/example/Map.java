package com.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map{
    public final static int WALL = 1;
    public final static int FREE = 0;

    private int[][] cells;
   
    public int getWidth(){
        return cells[0].length;
    }

    public int getHeight(){
        return cells.length;
    }

    public int getCell (int x, int y){
        return cells[y][x];
    }

    public int getCell (Point point){
        return cells[point.y][point.x];
    }
    
    public boolean isInBounds(Point point){
        return point.x >= 0 && point.y >= 0 &&
               point.x < getWidth() && point.y < getHeight(); 
    }
    public boolean isSuitableToVisit(Point point){
        return isInBounds(point) && getCell(point) == Map.FREE;
    }

    public String getOutput(){
        String result = "";
        for (int i = 0; i < cells.length; i++)
        {
            int[] row = cells[i];
            for(int j = 0; j < row.length; j++)
            {
                result += row[j];
                result += " ";
            }
            result += "\n";
        }
        return result;
    } 

    public Map(int width, int height){
        cells = new int[height][width];
    } 
    
    public Map(int[][] cells){
        this.cells = cells;
    }

    public Map(String mapString) {
        String[] rows = mapString.split(";");

        int height = rows.length;
        int width = rows[0].trim().split("\\s+").length;

        cells = new int[height][width];

        for (int i = 0; i < height; i++) {
            String[] nums = rows[i].trim().split("\\s+");
            for (int j = 0; j < width; j++) {
                cells[i][j] = Integer.parseInt(nums[j]);
            }
        }
    }
}
