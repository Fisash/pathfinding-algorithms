package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

import java.util.Arrays;
import java.util.List;

class ConfigList {
    private List<ConfigPanel> items;

    private int selectedIndex;

    public void moveUp() {
        selectedIndex = Math.max(0, selectedIndex - 1);
    }

    public void moveDown() {
        selectedIndex = Math.min(items.size() - 1, selectedIndex + 1);
    }

    ConfigList(String loadPath) {
        loadFromPath(loadPath);
    }

    public void loadFromPath(String loadPath) {

        items = Arrays.asList(
            new ConfigPanel("Первый пункт", "1"),
            new ConfigPanel("Первый пункт", "1"),
            new ConfigPanel("Первый пункт", "1"),
            new ConfigPanel("Первый пункт", "1")
        );
    }    
    
    public void draw (TextGraphics tg, int xPos, int yPos) {
        for (int i = 0; i < items.size(); i++) {
            if (i == selectedIndex) {
                tg.setForegroundColor(TextColor.ANSI.BLACK);
                tg.setBackgroundColor(TextColor.ANSI.WHITE);
            }
            else {
                tg.setForegroundColor(TextColor.ANSI.WHITE);
                tg.setBackgroundColor(TextColor.ANSI.BLACK);
            }
            tg.putString(xPos, yPos + i, items.get(i).getName());
        }
    }

    public void handleInput() {

    }
}
