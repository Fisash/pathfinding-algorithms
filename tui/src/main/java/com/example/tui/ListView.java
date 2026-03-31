package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

public class ListView extends Component {
    private List<Button> items = new ArrayList<>();
    private int selectedIndex = 0;

    public void addItem(String text, Runnable action) {
        Button btn = new Button(text, x+2, y+1+items.size(), action);
        btn.setParent(this);
        items.add(btn);
        if (items.size() == 1) {
            if (parent != null)
                parent.setFocusedChild(this);
        }
        updateSelectionDisplay();
    }

    private void updateSelectionDisplay() {
        for (int i = 0; i < items.size(); i++) {
            if (i == selectedIndex)
                items.get(i).setDisplayText("[" + items.get(i).getOriginalLabel() + "]");
            else
                items.get(i).resetDisplayText();
        }
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (selectedIndex >= 0 && selectedIndex < items.size()) {
            if (items.get(selectedIndex).handleInput(key))
                return true;
        }

        if (key.getKeyType() == KeyType.ArrowUp) {
            if (selectedIndex > 0) {
                selectedIndex--;
                updateSelectionDisplay();
                return true;
            }
        }

        if (key.getKeyType() == KeyType.ArrowDown) {
            if (selectedIndex < items.size() - 1) {
                selectedIndex++;
                updateSelectionDisplay();
                return true;
            }
        }

        return super.handleInput(key);
    }

    @Override
    public void setFocusedChild(Component child) {
    }

    @Override
    public Component getFocusedChild() {
        return null;
    }

    @Override
    public void draw(TextGraphics tg, boolean isFocused) {
        drawBorder(tg);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw(tg, isFocused && (i == selectedIndex));
        }
    }

    private void drawBorder(TextGraphics tg) {
        if (items.isEmpty()) return;

        int maxTextWidth = 0;
        for (Button btn : items) {
            int len = btn.getOriginalLabel().length() + 2;
            if (len > maxTextWidth) maxTextWidth = len;
        }
        int borderWidth = maxTextWidth + 4;
        int borderHeight = items.size() + 2;

        BorderDrawer.drawBorder(tg, x, y, borderWidth, borderHeight, BorderDrawer.BorderStyle.SINGLE);
    }
}
