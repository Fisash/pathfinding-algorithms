package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;

public class Panel extends Container {

    protected BorderDrawer.BorderStyle borderStyle;

    public Panel(int x, int y, int width, int height, BorderDrawer.BorderStyle borderStyle) {
        super(x, y, width, height);
        this.borderStyle = borderStyle;
    }

    @Override
    public void draw(TextGraphics tg, boolean isFocused) {
        if (width > 0 && height > 0)
            BorderDrawer.drawBorder(tg, x, y, width, height, borderStyle);
        for (Component child : children)
            child.draw(tg, isFocused && child == focusedChild);
    }
}
