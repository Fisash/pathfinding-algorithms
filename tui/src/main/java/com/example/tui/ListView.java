package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

public class ListView extends Panel {

    private int selectedIndex = 0;
    private boolean isFixedSize;

    public ListView (int x, int y, int width, int height, 
        BorderDrawer.BorderStyle borderStyle) {
        super(x, y, width, height, borderStyle);
        isFixedSize = true;
    }

    public ListView(int x, int y, BorderDrawer.BorderStyle borderStyle) {
        super(x, y, 0, 0, borderStyle);
        isFixedSize = false;
    }

    public void add(String text, Runnable action) {
        Button btn = new Button(text, x+2, y+1+children.size(), action);
        btn.setParent(this);
        children.add(btn);
        if (children.size() == 1) {
            focusedChild = btn;
            if (parent != null)
                parent.setFocusedChild(this);
        }
        updateSelectionDisplay();
        if(!isFixedSize) updateSize();
    }

    private void updateSelectionDisplay() {
        for (Component child : children) {
            Button item = (Button)child;
            if (focusedChild == child)
                item.setDisplayText("[" + item.getOriginalLabel() + "]");
            else
                item.resetDisplayText();
        }
    }

    private void scroll(boolean isUp) {
        selectedIndex += isUp ? -1 : 1;
        setFocusedChild(children.get(selectedIndex)); 
        updateSelectionDisplay();
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp) {
            if (selectedIndex > 0) {
                scroll(true);
                return true;
            }
        }

        if (key.getKeyType() == KeyType.ArrowDown) {
            if (selectedIndex < children.size() - 1) {
                scroll(false);
                return true;
            }
        }

        //is not a listview scrolling (or we try go up in uppest element or go down in downest element)
        return super.handleInput(key); //then try base navigation as a listview (but no list element)
    }

    private void updateSize() {
        if (children.isEmpty()) return;

        int maxTextWidth = 0;
        for (Component child : children) {
            Button btn = (Button) child;
            int len = btn.getOriginalLabel().length() + 2;
            if (len > maxTextWidth) maxTextWidth = len;
        }
        width = maxTextWidth + 4;
        height = children.size() + 2;
    }
}
