package com.example.console;

import com.example.text.RenderColor;

public class ConsoleColorAdapter {

    public static String toAnsi(RenderColor color) {
        return switch(color) {
            case DEFAULT -> "\033[0m";
            case BLACK -> "\033[30m";
            case RED -> "\033[31m";
            case GREEN -> "\033[32m";
            case YELLOW -> "\033[33m";
            case BLUE -> "\033[34m";
            case WHITE -> "\033[37m";
            default -> "\033[0m"; 
        };
    }

    public static String wrap(String text, RenderColor fg, RenderColor bg) {
        String fgCode = toAnsi(fg);
        String bgCode = toAnsiBackground(bg);
        return bgCode + fgCode + text + toAnsi(RenderColor.DEFAULT);
    }

    private static String toAnsiBackground(RenderColor color) {
        return switch(color) {
            case DEFAULT -> "\033[0m";
            case BLACK -> "\033[40m";
            case RED -> "\033[41m";
            case GREEN -> "\033[42m";
            case YELLOW -> "\033[43m";
            case BLUE -> "\033[44m";
            case WHITE -> "\033[47m";
            default -> "\033[0m"; 
        };
    }
}
