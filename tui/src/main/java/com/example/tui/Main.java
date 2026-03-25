package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);

        ConfigList configList = new ConfigList("./configs", 4, 5);

        Button createNew = new Button ("[Create new]", 3, 3, configList::createNew); 
        Component currentFocus = createNew;

        if(configList.getFirst() != null) {
            createNew.down = configList.getFirst();
            configList.getFirst().up = createNew;
        }

        while (true) {
            screen.clear();
            TextGraphics tg = screen.newTextGraphics();
            tg.putString(2, 1, "Path-finding-TUI");

            configList.draw(tg, currentFocus);
            createNew.draw(tg, currentFocus == createNew);

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) break;
            
            if (currentFocus != null)
                currentFocus = currentFocus.handleInput(key);
        }
        screen.stopScreen();
    }
}
