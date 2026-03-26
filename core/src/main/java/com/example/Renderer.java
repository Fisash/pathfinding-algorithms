package com.example;

public abstract class Renderer {
    
    private RenderCell[][] buffer;
    
    public int getBufferRows() {
        return buffer.length;
    }

    public int getBufferCols() {
        return buffer[0].length;
    }

    Renderer(Map map) {
        initBuffer(map);
    }

    public void initBuffer(int width, height) {
        buffer = new RenderCell[height][width];
    }

    public void initBuffer(Map map) {
        int width = map.getWidth();
        int height = mep.getHeight();

        initBuffer(width, height);
    } 

    public void fillMap(Map map) {
        int rows = getBufferRows();
        int cols = getBufferCols();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int mapCellType = map.getCell(j, i);
                RenderCell.PathType pathType = RenderCell.PathType.NotPath;
                buffer[i][j] = new RenderCell(mapCelType, 0, pathType);
            }
        }
    }

    public void draw(Map map, java.util.List<Point> path);

    public void stateHandle 
        (Map map, int [][] distances, java.util.List<Point> statePath);
}
