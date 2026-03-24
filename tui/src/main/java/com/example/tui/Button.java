package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Button extends Component {
    private String label;
    private Runnable action; 

    public Button(String label, int x, int y, Runnable action) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.action = action;
    }

    @Override
    public Component handleInput(KeyStroke key) {
        Component next = super.handleInput(key);
        if (next != this) return next;

        if (key.getKeyType() == KeyType.Enter && action != null) {
            action.run();
        }
        return this;
    }

    @Override
    public void draw(TextGraphics tg, boolean isFocused) {
        if (isFocused) {
            tg.setBackgroundColor(TextColor.ANSI.WHITE);
            tg.setForegroundColor(TextColor.ANSI.BLACK);
        } else {
            tg.setBackgroundColor(TextColor.ANSI.BLACK);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
        }

        tg.putString(x, y, " " + label + " ");

        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
    }
}
