package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

abstract class Component {
    protected Component parent;
    protected Component up, down, left, right;

    public void setParent(Component parent) { 
        this.parent = parent;
    }

    public Component getParent() { return parent; }

    public void setNeighbors(Component up, Component down, Component left, Component right) {
        this.up = up; this.down = down; this.left = left; this.right = right;
    }

    protected int x, y;

    public boolean handleInput(KeyStroke key) {
        if (isNavigationKey(key)) {
            Component neighbor = getNeighbor(key);
            if (neighbor != null) {
                transferFocus(neighbor);
                return true;
            }
        }
        return false;
    }

    protected boolean isNavigationKey(KeyStroke key) {
        KeyType type = key.getKeyType();
        return type == KeyType.ArrowUp || type == KeyType.ArrowDown ||
               type == KeyType.ArrowLeft || type == KeyType.ArrowRight;
    }

    protected Component getNeighbor(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowUp: return up;
            case ArrowDown: return down;
            case ArrowLeft: return left;
            case ArrowRight: return right;
            default: return null;
        }
    }

    protected void transferFocus(Component target) {
        if (parent != null) {
            parent.setFocusedChild(target);
        }
    }

    public void setFocusedChild(Component child) {}
    public Component getFocusedChild() { return null; }

    public abstract void draw(TextGraphics tg, boolean isFocused);
}
