package com.example.tui;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import java.util.ArrayList;
import java.util.List;

public class Panel extends Component {
    protected List<Component> children = new ArrayList<>();
    protected Component focusedChild;

    private int borderWidth = 40;
    private int borderHeight = 25;
    private BorderDrawer.BorderStyle borderStyle = BorderDrawer.BorderStyle.SINGLE;

    public Panel() {}

    public Panel(int width, int height) {
        this.borderWidth = width;
        this.borderHeight = height;
    }

    public void add(Component child, int x, int y) {
        child.setParent(this);
        child.x = x;
        child.y = y;
        children.add(child);
        if (focusedChild == null) focusedChild = child;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (focusedChild != null && focusedChild.handleInput(key)) {
            return true;
        }
        return super.handleInput(key);
    }

    @Override
    public void setFocusedChild(Component child) {
        if (child != null && child.getParent() == this)
            focusedChild = child;
    }

    @Override
    public Component getFocusedChild() {
        return focusedChild;
    }

    @Override
    public void draw(TextGraphics tg, boolean isFocused) {
        if (borderWidth > 0 && borderHeight > 0)
            BorderDrawer.drawBorder(tg, x, y, borderWidth, borderHeight, borderStyle);
        for (Component child : children)
            child.draw(tg, child == focusedChild);
    }
}
