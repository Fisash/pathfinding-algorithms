package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

abstract class Component {
    protected Container parent;
    protected Component up, down, left, right;

    private Runnable onFocusGainedCallback;
    private Runnable onFocusLostCallback;

    public void setFocusGainedCallback(Runnable action) {
        this.onFocusGainedCallback = action;
    }

    public void setFocusLostCallback(Runnable action) {
        this.onFocusLostCallback = action;
    }

    public void setParent(Container parent) { 
        this.parent = parent;
    }
    public Container getParent() { 
        return parent; 
    }

    public void setNeighbors(Component up, Component down, Component left, Component right) {
        this.up = up; this.down = down; this.left = left; this.right = right;
    }

    public void linkDown(Component neighbor) {
        down = neighbor;
        neighbor.up = this;
    }

    public void linkUp(Component neighbor) {
        up = neighbor;
        neighbor.down = this;
    }

    public void linkRight(Component neighbor) {
        right = neighbor;
        neighbor.left = this;
    }

    public void linkLeft(Component neighbor) {
        left = neighbor;
        neighbor.right = this;
    }

    protected int x, y;
    protected Component (int x, int y) {
        this.x = x;
        this.y = y;
    }

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
        if (parent != null)
            parent.setFocusedChild(target);
    }

    protected void onFocusGained() {
        if (onFocusGainedCallback != null)
            onFocusGainedCallback.run();
    }
    protected void onFocusLost() {
        if (onFocusLostCallback != null)
            onFocusLostCallback.run();
    }

    public abstract void draw(TextGraphics tg, boolean isFocused);
}
