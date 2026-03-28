package com.example;

public class RenderCell {

    public static enum PathRole {
        START, INTERMEDIATE_FINISH, FINISH
    }

    public boolean isWall;
    public boolean isVisited;
    public boolean isOnOpenSet;
    public boolean isPath;
    public int value;
    public PathRole role;

    public RenderCell() {}
    
    public RenderCell (boolean isWall, boolean isVisited, boolean isOnOpenSet,
                boolean isPath, int value, PathRole role) { 
        this.isWall = isWall;
        this.isVisited = isVisited;
        this.isOnOpenSet = isOnOpenSet;
        this.isPath = isPath;
        this.value = value;
        this.role = role;
    }
    
}
