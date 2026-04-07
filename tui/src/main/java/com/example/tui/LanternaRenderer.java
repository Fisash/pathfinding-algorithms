package com.example.tui;

import com.example.Map;
import com.example.text.TextCell;
import com.example.text.TextRenderConfig;
import com.example.text.TextRenderer;
import com.example.text.BorderStyle;
import com.googlecode.lanterna.graphics.TextGraphics;

public class LanternaRenderer extends TextRenderer {

    private TextGraphics tg;
    public void setGraphics(TextGraphics tg) {
        this.tg = tg;
        borderDrawer = new LanternaBorderDrawer(new BorderStyle(), tg);
    }
    
    public LanternaRenderer(Map map, TextGraphics tg, TextRenderConfig config) {
        super(map, config, new LanternaBorderDrawer(new BorderStyle(), tg));
        cellWidth = 2;
    }

    private int cellOffsetX = 1;
    private int cellOffsetY = 1;

    @Override
    protected void drawCell(int x, int y, TextCell cell) {
        if (tg == null) return;

        tg.setForegroundColor(LanternaColorAdapter.toLanterna(cell.fg));
        tg.setBackgroundColor(LanternaColorAdapter.toLanterna(cell.bg));
        String content = padToWidth(cell.text);
        tg.putString(x * cellWidth + cellOffsetX, y+cellOffsetY, content);
    }
    @Override
    public void draw() {
        LanternaBorderDrawer bd = (LanternaBorderDrawer) borderDrawer;
        bd.drawBorder(0, 0, cols * cellWidth, rows); 

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                TextCell cell = mapCell(buffer[y][x]);
                drawCell(x, y, cell);
            }
        }
    }

    @Override
    protected void endRow() {}
}
