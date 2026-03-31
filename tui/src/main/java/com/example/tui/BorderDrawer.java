package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

public class BorderDrawer {

    public enum BorderStyle {
        SINGLE,
        DOUBLE
    }

    public static void drawBorder(TextGraphics tg, int x, int y, int width, int height, BorderStyle style) {
        char topLeft, topRight, bottomLeft, bottomRight, horizontal, vertical;

        switch (style) {
            case DOUBLE:
                topLeft = '╔';
                topRight = '╗';
                bottomLeft = '╚';
                bottomRight = '╝';
                horizontal = '═';
                vertical = '║';
                break;
            case SINGLE:
            default:
                topLeft = '┌';
                topRight = '┐';
                bottomLeft = '└';
                bottomRight = '┘';
                horizontal = '─';
                vertical = '│';
        }

        TextColor originalFg = tg.getForegroundColor();
        TextColor originalBg = tg.getBackgroundColor();

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);

        tg.putString(x, y, String.valueOf(topLeft));
        for (int i = 1; i <= width; i++) {
            tg.putString(x + i, y, String.valueOf(horizontal));
        }
        tg.putString(x + width + 1, y, String.valueOf(topRight));

        for (int i = 1; i <= height; i++) {
            tg.putString(x, y + i, String.valueOf(vertical));
            tg.putString(x + width + 1, y + i, String.valueOf(vertical));
        }

        tg.putString(x, y + height + 1, String.valueOf(bottomLeft));
        for (int i = 1; i <= width; i++) {
            tg.putString(x + i, y + height + 1, String.valueOf(horizontal));
        }
        tg.putString(x + width + 1, y + height + 1, String.valueOf(bottomRight));

        tg.setForegroundColor(originalFg);
        tg.setBackgroundColor(originalBg);
    }
}
