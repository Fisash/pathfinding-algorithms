package com.example;

class RenderCell {

    public static enum PathType {
        NotPath, DefaultNode, Start, IntermediateCurrentNode, Finish
    }

    int mapCellType;
    int intValue;
    PathType pathType;

    RenderCell (int mapCellType, int intValue, PathType pathType) {
        this.mapCellType = mapCellType;
        this.pathType = pathType;
        this.intValue = intValue;
    }
    
}
