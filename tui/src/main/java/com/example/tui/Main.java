package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);

        Panel root = new Panel();
        root.x = 0;
        root.y = 2;

        ListView configListView = new ListView();
        configListView.x = 3;
        configListView.y = 5;
        loadConfigs("./configs", configListView);

        Button createNew = new Button("Create new", 3, 3, () -> {
            System.out.println("create new config (to be implemented)");
        });

        root.add(configListView, 3, 5);
        root.add(createNew, 3, 3);

        createNew.down = configListView;
        configListView.up = createNew;

        root.setFocusedChild(createNew);

        while (true) {
            screen.clear();
            TextGraphics tg = screen.newTextGraphics();
            tg.putString(40, 1, "Path-finding-TUI");

            root.draw(tg, false); 

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) break;

            root.handleInput(key);
        }

        screen.stopScreen();
    }

    private static void loadConfigs(String path, ListView listView) {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory()) return;

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".properties"));
        if (files == null) return;

        for (File file : files) {
            String name = file.getName().replace(".properties", "");
            String fullPath = file.getPath();
            listView.addItem(name, () -> {
                System.out.println("Selected config: " + fullPath);
                // Здесь будет открытие редактора конфига
            });
        }
    }
}
