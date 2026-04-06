package com.example.console;

import com.example.Map;
import com.example.RenderCell;
import com.example.PathFindingState;
import com.example.text.TextCell;
import com.example.text.TextRenderConfig;
import com.example.text.TextRenderer;

public class ConsoleRenderer extends TextRenderer {

    private final int msDisplayStateDelay;
    private int cellWidthForAnimation;

    public ConsoleRenderer(int msDisplayStateDelay, ConsoleBorderDrawer borderDrawer, Map map) {
        super(map, new TextRenderConfig(), borderDrawer);
        this.msDisplayStateDelay = msDisplayStateDelay;
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
        if (x < cols - 1)
            System.out.print(cellSeparator);
    }

    @Override
    protected void beforeDraw() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    protected void afterDraw() {
        try {
            Thread.sleep(msDisplayStateDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Override
    protected void endRow() {
        System.out.println();
    }
}
