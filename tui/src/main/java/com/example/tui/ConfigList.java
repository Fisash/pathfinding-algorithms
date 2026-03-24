package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigList {
    private List<Button> buttons = new ArrayList<>();

    public ConfigList(String loadPath, int startX, int startY) {
        loadFromPath(loadPath, startX, startY);
    }

    private void loadFromPath(String loadPath, int x, int y) {
        String[] names = {"Первый пункт", "Второй пункт", "Третий пункт", "Четвертый пункт"};

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            Button btn = new Button(name, x, y + i, () -> {
                System.out.println("Нажато: " + name);
            });
            buttons.add(btn);
        }

        for (int i = 0; i < buttons.size(); i++) {
            if (i > 0) 
                buttons.get(i).up = buttons.get(i - 1);
            
            if (i < buttons.size() - 1)
                buttons.get(i).down = buttons.get(i + 1);
            
        }
    }

    public void draw (TextGraphics tg, Component focus) {
        for (Button btn : getButtons())
            btn.draw(tg, btn == focus);
    }

    public Button getFirst() {
        return buttons.isEmpty() ? null : buttons.get(0);
    }

    public List<Button> getButtons() {
        return buttons;
    }
}
