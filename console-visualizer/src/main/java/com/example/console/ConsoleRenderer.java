package com.example.console;

import com.example.Map;
import com.example.RenderCell;
import com.example.PathFindingState;
import com.example.text.TextCell;
import com.example.text.TextRenderConfig;
import com.example.text.TextRenderer;

public class ConsoleRenderer extends TextRenderer {

    private final ConsoleFrame frame;
    private final int msDisplayStateDelay;
    private String cellSeparator = "";
    private int cellWidth;
    private int cellWidthForAnimation;
    private String widthFiller = " ";

    public ConsoleRenderer(int msDisplayStateDelay, ConsoleFrame frame, Map map) {
        super(map, new TextRenderConfig());
        this.msDisplayStateDelay = msDisplayStateDelay;
        this.frame = frame;
        this.cellWidthForAnimation = evaluateCellWidth(map);
        startAnimation();
    }

    @Override
    public void startAnimation() {
        super.startAnimation();
        cellWidth = cellWidthForAnimation;
        cellSeparator = "";
    }

    @Override
    public void endAnimation() {
        super.endAnimation();
        cellWidth = 2;
        cellSeparator = "";
    }

    @Override
    protected void drawCell(int x, int y, TextCell cell) {
        String s = padToWidth(cell.text);
        s = ConsoleColorAdapter.wrap(s, cell.fg, cell.bg);
        System.out.print(s);
        if (x < getBufferCols() - 1)
            System.out.print(cellSeparator);
        if (x == getBufferCols() - 1)
            System.out.println();
    }

    private String padToWidth(String s) {
        int len = s.length();
        if (len < cellWidth) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cellWidth - len; i++)
                sb.append(widthFiller);
            sb.append(s);
            return sb.toString();
        } else if (len > cellWidth)
            return s.substring(len - cellWidth);
        return s;
    }

    @Override
    public void draw() {
        clear(); 
        int rows = getBufferRows();
        int cols = getBufferCols();
        int separatorWidth = cellSeparator.length();

        frame.drawTopLine(cols, cellWidth, separatorWidth);

        for (int y = 0; y < rows; y++) {
            System.out.print(frame.v);
            for (int x = 0; x < cols; x++) {
                TextCell cell = mapCell(buffer[y][x]);
                String s = padToWidth(cell.text);
                s = ConsoleColorAdapter.wrap(s, cell.fg, cell.bg);
                System.out.print(s);
                if (x < cols - 1)
                    System.out.print(cellSeparator);
            }
            System.out.print(frame.v + "\n");
        }

        frame.drawBottomLine(cols, cellWidth, separatorWidth);
        afterDraw();
    }

    @Override
    protected void afterDraw() {
        try {
            Thread.sleep(msDisplayStateDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int evaluateCellWidth(Map map) {
        int maxDist = map.getWidth() * map.getHeight();
        return String.valueOf(maxDist).length();
    }
}
