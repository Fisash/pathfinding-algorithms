package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

abstract class Component {
    public Component up, down, left, right;

    protected int x, y;

    public Component handleInput(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp && up != null)
            return up;
        if (key.getKeyType() == KeyType.ArrowDown && down != null)
            return down;
        if (key.getKeyType() == KeyType.ArrowLeft && left != null)
            return left;
        if (key.getKeyType() == KeyType.ArrowRight && right != null)
            return right;

        return this; 
    }

    public abstract void draw(TextGraphics tg, boolean isFocused);
}
