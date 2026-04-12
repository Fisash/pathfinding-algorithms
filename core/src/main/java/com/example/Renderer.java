package com.example;

import java.util.List;

public abstract class Renderer {
    
    protected RenderCell[][] buffer;
    protected boolean isAnimationGoing;

    public void startAnimation() {
        isAnimationGoing = true;
    }

    public void endAnimation() {
        isAnimationGoing = false;
    }

    protected int rows, cols;
    public int getBufferRows() {
        return rows;
    }
    public int getBufferCols() {
        return cols;
    }

    public boolean isInBounds(Point point){
        return point != null && point.x >= 0 && point.y >= 0 &&
               point.x < getBufferCols() && point.y < getBufferRows(); 
    }

    protected Renderer(Map map) {
        initBuffer(map);
        fillMap(map);
    }

    protected void initBuffer(Map map) {
        initBuffer(map.getHeight(), map.getWidth());
    }

    protected void initBuffer(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        buffer = new RenderCell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                buffer[i][j] = new RenderCell();
        }
    }

    //clear isPath and role in buffer
    private void clearPath() {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++)
            {
                buffer[i][j].isPath = false;
                buffer[i][j].role = null;
            }
        }
    }

    public void fillPath(List<Point> path) {
        fillPath(path, null);
    }

    //fill isPath and role in buffer
    public void fillPath(List<Point> path, Point finish) {
        clearPath();
        for (Point p : path){
            if(!isInBounds(p))
                continue;
            buffer[p.y][p.x].isPath = true;
        } 

        Point start = path.get(0);
        if (isInBounds(start))
            buffer[start.y][start.x].role = RenderCell.PathRole.START;

        boolean isFullPath = finish == null;
        if(isFullPath)
            finish = path.get(path.size() - 1);

        if (isInBounds(finish)){
            buffer[finish.y][finish.x].role = RenderCell.PathRole.FINISH;
            buffer[finish.y][finish.x].isPath = true;
        }

        if(isFullPath)
            return;

        Point currentFinish = path.get(path.size() - 1);
        if (isInBounds(currentFinish)) {
            buffer[currentFinish.y][currentFinish.x].role =
            RenderCell.PathRole.INTERMEDIATE_FINISH;
        }
    }

    //fill value in buffer
    public void fillIntValues(int[][] values) {
        int newValue;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newValue = values[i][j];
                buffer[i][j].value = newValue;
            }
        }
    }
    
    //fill isOnOpenSet in buffer
    public void fillOpenSet(boolean[][] values) {
        boolean newValue;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newValue = values[i][j];
                buffer[i][j].isOnOpenSet = newValue;
            }
        }
    }

    //fill isVisited in buffer
    public void fillVisits(boolean[][] visits) {
        boolean newValue;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newValue = visits[i][j];
                buffer[i][j].isVisited = newValue;
            }
        }
    }

    //clear isVisited and role in buffer
    private void clearVisits() {
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++)
                buffer[i][j].isVisited = false;
        }
    }
    //fill isWall in buffer
    public void fillMap(Map map) {
        if(map.getHeight() != rows || map.getWidth() != cols) 
            initBuffer(map);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
               buffer[i][j].isWall = map.isWall(j, i);
        }
    }

    public abstract void draw();

    public void update(PathFindingState state) {
        fillVisits(state.visited);
        //fillOpenSet(state.openSet);
        fillPath(state.currentPath, state.finish);
        fillIntValues(state.values);
        draw();
    }

    public void drawPath(List<Point> path) {
        clearVisits();
        fillPath(path);
        draw();
    }
}
