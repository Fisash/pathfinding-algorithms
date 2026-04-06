package com.example.tui;

import com.example.text.BorderDrawer;
import com.example.text.BorderStyle;
import com.googlecode.lanterna.graphics.TextGraphics;

public class LanternaBorderDrawer extends BorderDrawer {

    private final TextGraphics tg;
    private int originX = 0;
    private int originY = 0;
    private int cursorX = 0;
    private int cursorY = 0;

    public LanternaBorderDrawer(BorderStyle style, TextGraphics tg) {
        super(style);
        this.tg = tg;
    }

    public void setOrigin(int x, int y) {
        this.originX = x;
        this.originY = y;
        setCursor(0, 0);
    }

    public void setCursor(int x, int y) {
        this.cursorX = x;
        this.cursorY = y;
    }

    @Override
    protected void drawChar(char c) {
        tg.setCharacter(originX + cursorX, originY + cursorY, c);
        cursorX++;
    }

    @Override
    protected void drawNewLine() {
        cursorY++;
        cursorX = 0;
    }

    public void drawBorder(int left, int top, int width, int height) {
        drawCharAt(left, top, style.topLeft);
        for (int i = 1; i <= width; i++)
            drawCharAt(left + i, top, style.horizontal);
        drawCharAt(left + width + 1, top, style.topRight);

        for (int i = 1; i <= height; i++) {
            drawCharAt(left, top + i, style.vertical);
            drawCharAt(left + width + 1, top + i, style.vertical);
        }

        drawCharAt(left, top + height + 1, style.bottomLeft);
        for (int i = 1; i <= width; i++)
            drawCharAt(left + i, top + height + 1, style.horizontal);
        drawCharAt(left + width + 1, top + height + 1, style.bottomRight);
    }

    private void drawCharAt(int x, int y, char c) {
        tg.setCharacter(x, y, c);
    }
}
