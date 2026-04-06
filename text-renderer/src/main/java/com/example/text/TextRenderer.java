package com.example.text;

import com.example.Map;
import com.example.RenderCell;
import com.example.Renderer;

public abstract class TextRenderer extends Renderer {

    protected TextRenderConfig config;
    protected BorderDrawer borderDrawer;

    protected int cellWidth;
    public int getCellWidth() {
        return cellWidth;
    }

    protected String cellSeparator = "";
    protected String widthFiller = " ";

    protected TextRenderer(Map map, TextRenderConfig config, BorderDrawer borderDrawer) {
        super(map);
        this.config = config;
        this.borderDrawer = borderDrawer;
        this.cellWidth = evaluateCellWidth(map);
    }

    protected TextCell mapCell(RenderCell cell) {
        TextCell view = new TextCell();

        if (cell.isWall) {
            view.text = config.wallSymbol;
            view.fg = config.wallFg;
            view.bg = config.wallBg;
            return view;
        }

        if (cell.isPath) {
            if (cell.role == null) {
                view.text = config.pathSymbol;
                view.fg = config.pathFg;
            } else if (cell.role == RenderCell.PathRole.START) {
                view.text = config.startSymbol;
                view.fg = config.startFg;
            } else if (cell.role == RenderCell.PathRole.FINISH) {
                view.text = config.finishSymbol;
                view.fg = config.finishFg;
            } else {
                view.text = config.intermediateFinishSymbol;
                view.fg = config.intermediateFinishFg;
            }
            view.bg = config.pathBg;
            return view;
        }

        if (!cell.isVisited) {
            view.text = config.unvisitedSymbol;
            view.fg = config.unvisitedFg;
            view.bg = config.unvisitedBg;
            return view;
        }

        view.text = String.valueOf(cell.value);
        view.fg = config.visitedFg;
        view.bg = config.visitedBg;

        return view;
    }

    protected abstract void drawCell(int x, int y, TextCell cell);

    @Override
    public void draw() {
        beforeDraw();
        if(borderDrawer != null)
            borderDrawer.drawTopLine(cols, cellWidth, cellSeparator.length());

        for (int y = 0; y < rows; y++) {
            if(borderDrawer != null)
                borderDrawer.drawVertical();
            for (int x = 0; x < cols; x++) {
                TextCell cell = mapCell(buffer[y][x]);
                drawCell(x, y, cell);
            }
            if(borderDrawer != null)
                borderDrawer.drawVertical();
            endRow();
        }
        if(borderDrawer != null)
            borderDrawer.drawBottomLine(cols, cellWidth, cellSeparator.length());
        afterDraw();
    }

    protected abstract void endRow();

    protected void afterDraw() {}
    protected void beforeDraw() {}

    protected static int evaluateCellWidth(Map map) {
        int maxDist = map.getWidth() * map.getHeight();
        return String.valueOf(maxDist).length();
    }

    protected String padToWidth(String s) {
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
}
