package com.example.text;

import com.example.Map;
import com.example.RenderCell;
import com.example.Renderer;

public abstract class TextRenderer extends Renderer {

    protected TextRenderConfig config;

    protected TextRenderer(Map map, TextRenderConfig config) {
        super(map);
        this.config = config;
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
        for (int y = 0; y < getBufferRows(); y++) {
            for (int x = 0; x < getBufferCols(); x++) {
                TextCell cell = mapCell(buffer[y][x]);
                drawCell(x, y, cell);
            }
        }
        afterDraw();
    }

    protected static int evaluateCellWidth(Map map) {
        int maxDist = map.getWidth() * map.getHeight();
        return String.valueOf(maxDist).length();
    }

    protected void afterDraw() {}
}
