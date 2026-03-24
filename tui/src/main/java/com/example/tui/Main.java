package com.example.tui;

import com.example.Config;

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

        ConfigList configList = new ConfigList("34");
        while (true) {
            screen.clear();
            TextGraphics tg = screen.newTextGraphics();
            tg.putString(2, 2, "Path-finding-TUI");

            configList.draw(tg, 4, 4);

            screen.refresh(); //draw screen buffer to terminal

            //input
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.Escape) break;
            
            if (key.getKeyType() == KeyType.ArrowDown) {
                configList.moveDown();
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                configList.moveUp();
            }
        }

        screen.stopScreen(); 
    }
}
