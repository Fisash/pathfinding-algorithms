package com.example.text;

public abstract class BorderDrawer {
    protected BorderStyle style;

    protected BorderDrawer() {
        style = new BorderStyle();
    }

    protected BorderDrawer(BorderStyle style) {
        this.style = style;
    }

    public void drawTopLine(int cols, int cellWidth, int cellSpacing) {
        drawChar(style.topLeft);
        for (int i = 0; i < cols; i++) {
            drawCharRepeated(style.horizontal, cellWidth);
            if (i < cols - 1)
                drawCharRepeated(style.horizontal, cellSpacing);
        }
        drawChar(style.topRight);
        drawNewLine();
    }

    public void drawBottomLine(int cols, int cellWidth, int cellSpacing) {
        drawChar(style.bottomLeft);
        for (int i = 0; i < cols; i++) {
            drawCharRepeated(style.horizontal, cellWidth);
            if (i < cols - 1)
                drawCharRepeated(style.horizontal, cellSpacing);
        }
        drawChar(style.bottomRight);
        drawNewLine();
    }

    public void drawVertical() {
        drawChar(style.vertical);
    }

    protected void drawCharRepeated(char c, int count) {
        for (int i = 0; i < count; i++)
            drawChar(c);
    }

    protected abstract void drawChar(char c);
    protected abstract void drawNewLine(); 
}
