package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class App {

    private Terminal terminal;
    private TerminalSize size;
    private Screen screen;
    
    private Panel root;
    public Panel getRoot() {
        return root;
    }

    public int getRootWidth() {
        return root.getWidth();
    }

    public int getRootHeight() {
        return root.getHeight();
    }

    public void addToRoot(Component child, int x, int y) {
        root.add(child, x, y);
    }
    
    public App() throws IOException {
        this(null);
    }

    public App(BorderDrawer.BorderStyle rootBorderStyle) throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null);

        size = screen.getTerminalSize();
        int rootWidth = size.getColumns() - 2;
        int rootHeight = size.getRows() - 2;

        root = new Panel(0, 0, rootWidth, rootHeight, rootBorderStyle);
    }

    public void run() throws IOException {
        while (true) {
            screen.clear();
            TextGraphics tg = screen.newTextGraphics();

            root.draw(tg, true); 

            screen.refresh();

            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) break;

            root.handleInput(key);
        }
        screen.stopScreen();
    }
}
