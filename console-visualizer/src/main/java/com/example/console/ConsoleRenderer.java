package com.example.console;

import com.example.Map;
import com.example.Renderer;
import com.example.RenderCell;
import com.example.PathFindingState;

import java.util.List;

public class ConsoleRenderer extends Renderer {

    private String BG_COLOR;

    private String WALL, PATHPOINT, START, FINISH, INTERM_FINISH, UNVISITED;

    private String RAW_WALL = " "; 
    private String RAW_PATHPOINT = "●";
    private String RAW_START = "S";
    private String RAW_FINISH = "F";
    private String RAW_INTERM_FINISH = "F";
    private String RAW_UNVISITED = " ";

    private String WIDTH_FILLER = " ";

    private String cellSeparator = "";

    private int cellWidth;

    private int cellWidthForAnimation; 

    private void initChars() {
        BG_COLOR = ConsoleColors.BG_BLACK;

        WALL = fillToWidth(RAW_WALL);
        PATHPOINT = fillToWidth(RAW_PATHPOINT);
        START = fillToWidth(RAW_START);
        FINISH = fillToWidth(RAW_FINISH);
        INTERM_FINISH = fillToWidth(RAW_INTERM_FINISH);
        UNVISITED = fillToWidth(RAW_UNVISITED);

        WALL = ConsoleColors.backgroundize(WALL, ConsoleColors.BG_BLUE);
        PATHPOINT = 
        ConsoleColors.wrap(PATHPOINT, ConsoleColors.RED, BG_COLOR);
        START = 
        ConsoleColors.wrap(START, ConsoleColors.RED, BG_COLOR);
        FINISH = 
        ConsoleColors.wrap(FINISH, ConsoleColors.RED, BG_COLOR);
        INTERM_FINISH = 
        ConsoleColors.wrap(INTERM_FINISH, ConsoleColors.YELLOW, BG_COLOR);
        UNVISITED = 
        ConsoleColors.backgroundize(UNVISITED, BG_COLOR);
    }

    private ConsoleFrameOptions frame;
    private final int msDisplayStateDelay; 

    @Override
    public void startAnimation() {
        super.startAnimation();
        cellWidth = cellWidthForAnimation;
        cellSeparator = "";
        initChars();
    }

    @Override
    public void endAnimation() {
        super.endAnimation();
        cellWidth = 2;
        cellSeparator = "";
        initChars();
    }

    public ConsoleRenderer(int msDisplayStateDelay, ConsoleFrameOptions frame, Map initMap) {
        super(initMap);
        cellWidthForAnimation = evaluateCellWidth(initMap);
        startAnimation();
        this.msDisplayStateDelay = msDisplayStateDelay; 
        this.frame = frame;
    }

    private String fillToWidth(String content){
       int length = content.length();
        if (length == cellWidth)
            return content;
        if (length < cellWidth) {
            int zerosToAdd = cellWidth - length;
            StringBuilder result = new StringBuilder(cellWidth);
            for (int i = 0; i < zerosToAdd; i++)
                result.append(WIDTH_FILLER);
            result.append(content);
            return result.toString();
        }
        else 
            return content.substring(length - cellWidth);
    }

    private String getValueCellView(int value) {
        String base = String.valueOf(value);
        base = fillToWidth(base);
        return ConsoleColors.wrap(base, ConsoleColors.WHITE, BG_COLOR);
    }

    public String stringView(RenderCell cell){
        if(cell.isWall)
            return WALL;
        if(cell.isPath) {
            if (cell.role == null) 
                return PATHPOINT;
            if (cell.role == RenderCell.PathRole.START)
                return START;
            if (cell.role == RenderCell.PathRole.FINISH)
                return FINISH;
            return INTERM_FINISH;
        }
        if(!cell.isVisited)
            return UNVISITED;
        return getValueCellView(cell.value); 
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int evaluateCellWidth(Map map) {
        int maxPossibleDist = map.getWidth() * map.getHeight();
        return String.valueOf(maxPossibleDist).length();
    }


    @Override
    public void update(PathFindingState state) {
        super.update(state);

        try {
            Thread.sleep(msDisplayStateDelay); 
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void draw(){
        clear();
        System.out.print(bufferToString());
    } 

    public String bufferToString () {
        StringBuilder result = new StringBuilder();
        int i, j;
        int rows = getBufferRows();
        int cols = getBufferCols();
        int separatorWidth = cellSeparator.length();

        frame.addTopLine(result, cols, cellWidth, separatorWidth);
        for(i = 0; i < rows; i++){
            result.append(frame.v_view);
            for(j = 0; j < cols; j++){
                result.append(stringView(buffer[i][j]));
                if (j < cols - 1)
                    result.append(cellSeparator);
            }
            result.append(frame.v_view);
            result.append("\n");
        }

        frame.addBottomLine(result, cols, cellWidth, separatorWidth);
        return result.toString();
    }
}
