package com.example.tui;

import com.example.text.RenderColor;
import com.googlecode.lanterna.TextColor;

public class LanternaColorAdapter {
    public static TextColor toLanterna(RenderColor color) {
        return switch (color) {
            case BLACK -> TextColor.ANSI.BLACK;
            case RED -> TextColor.ANSI.RED;
            case GREEN -> TextColor.ANSI.GREEN;
            case YELLOW -> TextColor.ANSI.YELLOW;
            case BLUE -> TextColor.ANSI.BLUE;
            case PURPLE -> TextColor.ANSI.MAGENTA;
            case CYAN -> TextColor.ANSI.CYAN;
            case WHITE -> TextColor.ANSI.WHITE;
            default -> TextColor.ANSI.DEFAULT;
        };
    }
}
