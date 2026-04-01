package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Button extends Component {
    private String label;
    private String displayText;          
    private Runnable action;

    public Button(String label, int x, int y, Runnable action) {
        super(x, y);
        this.label = label;
        this.action = action;
        this.displayText = label;
    }

    public String getOriginalLabel() {
        return label;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public void resetDisplayText() {
        this.displayText = label;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (super.handleInput(key)) //is a navigation key event
            return true;

        if (key.getKeyType() == KeyType.Enter && action != null) {
            action.run();
            return true;
        }
        return false;
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

        tg.putString(x, y, " " + displayText + " ");

        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
    }
} 
