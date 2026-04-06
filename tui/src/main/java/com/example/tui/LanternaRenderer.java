package com.example.tui;

import com.example.Map;
import com.example.text.TextCell;
import com.example.text.TextRenderConfig;
import com.example.text.TextRenderer;
import com.googlecode.lanterna.graphics.TextGraphics;

public class LanternaRenderer extends TextRenderer {

    private TextGraphics tg;
    private int cellWidth;
    
    public LanternaRenderer(Map map, TextRenderConfig config) {
        super(map, config);
        this.cellWidth = evaluateCellWidth(map);
    }

    public void setGraphics(TextGraphics tg) {
        this.tg = tg;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    @Override
    protected void drawCell(int x, int y, TextCell cell) {
        if (tg == null) return;

        tg.setForegroundColor(LanternaColorAdapter.toLanterna(cell.fg));
        tg.setBackgroundColor(LanternaColorAdapter.toLanterna(cell.bg));
        String content = padToWidth(cell.text);
        tg.putString(x * cellWidth, y, content);
    }

    private String padToWidth(String s) {
        if (s.length() >= cellWidth) return s.substring(0, cellWidth);
        return " ".repeat(cellWidth - s.length()) + s;
    }
}
