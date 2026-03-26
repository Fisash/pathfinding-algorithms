package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigList {
    private List<ConfigPanel> panels = new ArrayList<>();

    public ConfigList(String loadPath, int startX, int startY) {
        loadFromPath(loadPath, startX, startY);
    }

    public void createNew() {
        loadFromPath("./config", 8, 8);
    }

    private void loadFromPath(String loadPath, int x, int y) {
        File folder = new File(loadPath);

        if (!folder.exists() || !folder.isDirectory()) {
            //dir is no found
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".properties"));

        panels = new ArrayList<>();

        int index = 0;

        for (File file : files) {
            String path = file.getPath();
            String name = file.getName().replace(".properties", "");

            Button btn = new Button(name, x, y + index, () -> {
                System.out.println("Выбран конфиг: " + path);
            });

            ConfigPanel panel = new ConfigPanel(path, btn);
            panels.add(panel);

            index++;
        }

        for (int i = 0; i < panels.size(); i++) {
            if (i > 0)
                panels.get(i).getButton().up = panels.get(i - 1).getButton();

            if (i < panels.size() - 1)
                panels.get(i).getButton().down = panels.get(i + 1).getButton();
        }
    }

    public void draw (TextGraphics tg, Component focus) {
        for (ConfigPanel panel : panels)
            panel.getButton().draw(tg, panel.getButton() == focus);
    }

    public Button getFirstButton() {
        return panels.isEmpty() ? null : panels.get(0).getButton();
    }
    public ConfigPanel getFirst() {
        return panels.isEmpty() ? null : panels.get(0);
    }

}
