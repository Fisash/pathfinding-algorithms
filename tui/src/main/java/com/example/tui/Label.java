package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

public class Label extends Component {
    private String text;

    public Label(String text, int x, int y) {
        super(x, y);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getWidth() {
        return text.length();
    }
    
    @Override
    public int getHeight() {
        return 1;
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

        tg.putString(x, y, text);

        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
    }
} 
