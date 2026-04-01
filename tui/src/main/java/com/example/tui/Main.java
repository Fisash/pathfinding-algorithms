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

    private static BorderDrawer.BorderStyle singleBorder = BorderDrawer.BorderStyle.SINGLE;

    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);

        Panel root = new Panel(0, 2, 40, 40, singleBorder);
        ListView configListView = new ListView(0, 0, singleBorder);
        loadConfigs("./configs", configListView);

        Button createNew = new Button("Create new", 0, 0, () -> {
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

            root.draw(tg, true); 

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
            listView.add(name, () -> {
                System.out.println("Selected config: " + fullPath);
                //todo: config editor open
            });
        }
    }
}
